import React, { useEffect } from 'react';
import Page from '../../../components/Page';
import { useState } from 'react';
import { Link as RouterLink, Navigate, useLocation, useParams } from 'react-router-dom';
import { Card, Stack, Button, Container, Typography, IconButton, InputAdornment } from '@mui/material';
import { ROUTES } from '../../../constants/routerConfig';
import Iconify from '../../../components/Iconify';
import * as Yup from 'yup';
import { useNavigate } from 'react-router-dom';
// form
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
// @mui
import { LoadingButton } from '@mui/lab';
// components
import { FormProvider, RHFTextField } from '../../../components/hook-form';
import crawlerProductService from '../services/CrawlerProductService';
import ProductAttributeItemForm from '../components/ProductAttributeItemForm';
import ListSubheader from '@mui/material/ListSubheader';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Collapse from '@mui/material/Collapse';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import SkeletonLoadingV1 from '../../shared/components/SkeletonLoadingV1';

const EditCrawlerProduct = () => {
  const navigate = useNavigate();
  const [crawlConfigs, setCrawlConfigs] = useState({});
  const { id } = useParams();
  const [data, setData] = useState({});
  const [openAttributes, setOpenAttributes] = useState(true);
  const [isLoading, setIsLoading] = useState(true);

  const RegisterSchema = Yup.object().shape({
    name: Yup.string().required('Name is required'),
    link: Yup.string().required('Link is required'),
    status: Yup.number().required('Status is required'),
    crawlConfigId: Yup.number().required('Crawl Config ID is required'),
  });

  const defaultValues = {
    name: '',
    link: '',
    crawlCategoryLink: '',
    status: 0,
    attributes: [],
  };

  const methods = useForm({
    resolver: yupResolver(RegisterSchema),
    defaultValues,
  });

  useEffect(() => {
    getDetailsData();
  }, []);

  const getDetailsData = async () => {
    await crawlerProductService.findById(id).then((res) => {
      const data = res.data?.data;
      setData(data);
      setValue('link', data?.link);
      setValue('attributes', data?.attributes);
      setValue('crawlCategoryLink', data?.crawlCategory?.link);
      setIsLoading(false);
    });
  };

  const {
    handleSubmit,
    formState: { isSubmitting, errors },
    getValues,
    setValue,
    watch,
  } = methods;

  const onSubmit = async (data) => {
    await crawlerCategoryService
      .update(id, data)
      .then((res) => {
        navigate(ROUTES.CRAWLER_CATEGORY, { replace: true });
      })
      .catch((err) => {});
  };

  const handleClickAttributes = () => {
    setOpenAttributes(!openAttributes);
  };

  return (
    <Page title="Crawl Categories">
      <Container maxWidth="xl">
        <Stack direction="row" alignItems="center" justifyContent="space-between" mb={5}>
          <Typography variant="h4" gutterBottom>
            Edit Crawler Product
          </Typography>
          <Button
            variant="contained"
            component={RouterLink}
            to={ROUTES.CRAWLER_PRODUCT}
            startIcon={<Iconify icon="eva:plus-fill" />}
          >
            Back
          </Button>
        </Stack>
        <Card sx={{ padding: '2rem' }}>
          {isLoading ? (
            <SkeletonLoadingV1 />
          ) : (
            <FormProvider methods={methods} onSubmit={handleSubmit(onSubmit)}>
              <Stack spacing={3}>
                <RHFTextField
                  name="link"
                  label="Link"
                  InputProps={{
                    readOnly: true,
                  }}
                />
                <RHFTextField
                  name="crawlCategoryLink"
                  label="Category Link"
                  InputProps={{
                    readOnly: true,
                  }}
                />
                <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}></Stack>
                <List
                  sx={{ width: '100%', bgcolor: 'background.paper' }}
                  component="nav"
                  aria-labelledby="nested-list-subheader"
                  subheader={
                    <ListSubheader component="div" id="nested-list-subheader">
                      Attributes
                    </ListSubheader>
                  }
                >
                  <ListItemButton onClick={handleClickAttributes}>
                    <ListItemIcon>
                      <InboxIcon />
                    </ListItemIcon>
                    <ListItemText primary={openAttributes ? 'Hidden' : 'Show'} />
                    {openAttributes ? <ExpandLess /> : <ExpandMore />}
                  </ListItemButton>
                  <Collapse in={openAttributes} timeout="auto" unmountOnExit>
                    <Stack padding={5}>
                      <Stack spacing={2}>
                        {watch('attributes').map((item, index) => (
                          <ProductAttributeItemForm
                            key={index}
                            config={item}
                            index={index}
                            onChangeConfigItem={() => {}}
                            watch={watch}
                            arrayName="attributes"
                            onRemoveConfigItem={() => {}}
                          />
                        ))}
                      </Stack>
                    </Stack>
                    <Button onClick={() => handleAddNewSelectorConfig()} variant="contained" color="primary">
                      Add New
                    </Button>
                  </Collapse>
                </List>
                <LoadingButton fullWidth size="large" type="submit" variant="contained" loading={isSubmitting}>
                  Submit
                </LoadingButton>
              </Stack>
            </FormProvider>
          )}
        </Card>
      </Container>
    </Page>
  );
};

export default EditCrawlerProduct;
