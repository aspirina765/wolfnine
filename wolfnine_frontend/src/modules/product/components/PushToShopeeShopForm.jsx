import React, { useEffect, useState } from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import * as Yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useForm } from 'react-hook-form';
import CustomSelectBoxV2 from '../../../components/hook-form/CustomSelectBoxV2';
import { FormProvider, RHFTextField } from '../../../components/hook-form';
import shopeeShopConfigService from '../../shopeeShopConfig/services/shopeeShopConfigService';
import { LoadingButton } from '@mui/lab';
import { Stack } from '@mui/material';
import { toast } from 'react-toastify';

const PushToShopeeShopForm = ({ open, onClose, onOpen, numSelected, productIds }) => {
  const [shopeeShopList, setShopeeShopList] = useState([]);
  const [shopeeCategoryList, setShopeeCategoryList] = useState([]);
  const [shopeeBrandList, setShopeeBrandList] = useState([]);
  const [shopeeLogisticChannelList, setShopeeLogisticChannelList] = useState([]);
  const [limitCategoryList, setLimitCategoryList] = useState(10);
  const [limitBrandList, setLimitBrandList] = useState(10);

  const RegisterSchema = Yup.object().shape({
    shopeeShopConfigId: Yup.number()
      .required('Shop ID is required')
      .notOneOf([0, null], 'Shop ID is required')
      .default(0),
    logisticId: Yup.number().required('Logistic is required').notOneOf([0, null], 'Shop ID is required').default(0),
    brandId: Yup.number().required('Brand is required').notOneOf([0, null], 'Shop ID is required').default(0),
    categoryId: Yup.number().required('Category is required').notOneOf([0, null], 'Shop ID is required').default(0),
    sellerStock: Yup.number().required('Seller stock is required'),
  });

  const defaultValues = {
    shopeeShopConfigId: 0,
    categoryId: 0,
    brandId: 0,
    logisticId: 0,
    sellerStock: 1000,
  };

  const methods = useForm({
    resolver: yupResolver(RegisterSchema),
    defaultValues,
  });

  const {
    handleSubmit,
    formState: { isSubmitting, errors },
    getValues,
    watch,
  } = methods;

  useEffect(() => {
    getShopeeShopList();
  }, []);

  useEffect(() => {
    getShopeeCategoryList();
    getLogisticChannelList();
  }, [watch('shopeeShopConfigId')]);

  useEffect(() => {
    getShopeeBrandList();
  }, [watch('categoryId')]);

  useEffect(() => {
    getShopeeCategoryList();
  }, [limitCategoryList]);

  useEffect(() => {
    getShopeeBrandList();
  }, [limitBrandList]);

  const getShopeeShopList = async () => {
    await shopeeShopConfigService.findAllByAuthUser().then((res) => {
      setShopeeShopList(res.data?.data?.content);
    });
  };

  const getShopeeCategoryList = async () => {
    await shopeeShopConfigService
      .getCategoryList(getValues('shopeeShopConfigId'), {
        limit: limitCategoryList,
      })
      .then((res) => {
        setShopeeCategoryList(res.data?.data ?? []);
      });
  };

  const getShopeeBrandList = async () => {
    await shopeeShopConfigService
      .getShopBrandList(getValues('shopeeShopConfigId'), {
        shopeeCategoryId: getValues('categoryId'),
        pageSize: limitBrandList,
      })
      .then((res) => {
        setShopeeBrandList(res?.data?.data ?? []);
      });
  };

  const getLogisticChannelList = async () => {
    await shopeeShopConfigService.getLogisticChannelList(getValues('shopeeShopConfigId')).then((res) => {
      setShopeeLogisticChannelList(res?.data?.data ?? []);
    });
  };

  const onSubmit = async (data) => {
    data.productIds = productIds;
    await shopeeShopConfigService
      .pushItemsToShopeeShop(data)
      .then((res) => {
        console.log('🚀 ~ file: PushToShopeeShopForm.jsx ~ line 99 ~ onSubmit ~ res', res);
        toast.success('Push item to Shopee Shop successfully ! !', {
          position: toast.POSITION.TOP_CENTER,
        });
      })
      .catch((err) => {
        toast.error('Error Notification !', {
          position: toast.POSITION.TOP_CENTER,
        });
      });
    onClose();
  };

  const loadMoreCategory = (e) => {
    const bottom = e.target.scrollHeight - e.target.scrollTop === e.target.clientHeight;
    if (bottom) {
      setLimitCategoryList((limit) => limit + 20);
    }
  };

  const loadMoreBrand = (e) => {
    const bottom = e.target.scrollHeight - e.target.scrollTop === e.target.clientHeight;
    if (bottom) {
      setLimitBrandList((limit) => {
        if (limit + 20 <= 100) {
          return limit + 20;
        } else {
          return 100;
        }
      });
    }
  };

  return (
    <>
      <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth={true}>
        <FormProvider methods={methods} onSubmit={handleSubmit(onSubmit)}>
          <DialogTitle>Push Product To Shopee Shop</DialogTitle>
          <DialogContent>
            <DialogContentText>
              <b>{numSelected} </b>
              items is choosed
            </DialogContentText>
            <Box sx={{ minWidth: 120 }} marginTop={3}>
              <Stack spacing={3}>
                <CustomSelectBoxV2
                  items={shopeeShopList}
                  itemKey={'id'}
                  itemLabel={'name'}
                  name="shopeeShopConfigId"
                  label="Shopee Shop"
                  firstOptionLabel={'Choose Shopee Shop'}
                />
                <CustomSelectBoxV2
                  items={shopeeCategoryList}
                  itemKey={'category_id'}
                  itemLabel={'display_category_name'}
                  name="categoryId"
                  label="Shopee Category"
                  firstOptionLabel={'Choose Category'}
                  disabled={watch('shopeeShopConfigId') == 0}
                  MenuProps={{
                    PaperProps: {
                      onScroll: loadMoreCategory,
                      style: {
                        maxHeight: 300,
                      },
                    },
                  }}
                />

                <CustomSelectBoxV2
                  items={shopeeBrandList}
                  itemKey={'brand_id'}
                  itemLabel={'display_brand_name'}
                  name="brandId"
                  label="Shopee Brand"
                  firstOptionLabel={'Choose Shopee Brand'}
                  disabled={watch('categoryId') == 0}
                  MenuProps={{
                    PaperProps: {
                      onScroll: loadMoreBrand,
                      style: {
                        maxHeight: 300,
                      },
                    },
                  }}
                />

                <CustomSelectBoxV2
                  items={shopeeLogisticChannelList}
                  itemKey={'logistics_channel_id'}
                  itemLabel={'logistics_channel_name'}
                  name="logisticId"
                  label="Shopee Logistic"
                  firstOptionLabel={'Choose Shopee Logistic'}
                  disabled={watch('shopeeShopConfigId') == 0}
                />
                <RHFTextField name="sellerStock" label="Seller Stock" disabled={watch('categoryId') == 0} />
              </Stack>
            </Box>
          </DialogContent>
          <DialogActions>
            <Button onClick={onClose}>Cancel</Button>
            <LoadingButton size="large" type="submit" variant="contained" loading={isSubmitting}>
              Push
            </LoadingButton>
          </DialogActions>
        </FormProvider>
      </Dialog>
    </>
  );
};

export default PushToShopeeShopForm;
