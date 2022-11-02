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
import { LoadingButton } from '@mui/lab';
import { Stack } from '@mui/material';
import { toast } from 'react-toastify';
import pushProductApiService from '../../pushProductApi/services/pushProductApiService';

const RegisterSchema = Yup.object().shape({
  pushProductApiConfigId: Yup.number()
    .required('Api Config is required')
    .notOneOf([0, null], 'Api Config is required')
    .default(0),
});

const PushToApiForm = ({ open, onClose, onOpen, numSelected, productIds }) => {
  const [apiConfigList, setApiConfigList] = useState({});

  const defaultValues = {
    pushProductApiConfigId: 0,
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
    getApiConfigList();
  }, []);

  const getApiConfigList = async () => {
    await pushProductApiService.findAll().then((res) => {
      setApiConfigList(res.data?.data);
    });
  };

  const onSubmit = async (data) => {
    data.productIds = productIds;
    await pushProductApiService
      .pushProductToApi(data)
      .then((res) => {
        console.log('🚀 ~ file: PushToApiForm.jsx ~ line 64 ~ onSubmit ~ res', res);
        toast.success('Push products to API successfully ! !', {
          position: toast.POSITION.TOP_RIGHT,
        });
      })
      .catch((err) => {
        toast.error('Error Notification !', {
          position: toast.POSITION.TOP_RIGHT,
        });
      });
    onClose();
  };

  return (
    <>
      <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth={true}>
        <FormProvider methods={methods} onSubmit={handleSubmit(onSubmit)}>
          <DialogTitle>Push Product To Api</DialogTitle>
          <DialogContent>
            <DialogContentText>
              <b>{numSelected} </b>
              items is choosed
            </DialogContentText>
            <Box sx={{ minWidth: 120 }} marginTop={3}>
              <Stack spacing={3}>
                <CustomSelectBoxV2
                  items={apiConfigList?.content}
                  itemKey={'id'}
                  itemLabel={'apiUrl'}
                  name="pushProductApiConfigId"
                  label="List API"
                  firstOptionLabel={'Choose API'}
                />
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

export default PushToApiForm;
