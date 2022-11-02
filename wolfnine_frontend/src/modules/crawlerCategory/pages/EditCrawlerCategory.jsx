import React, { useEffect } from 'react';
import Page from '../../../components/Page';
import { filter } from 'lodash';
import { sentenceCase } from 'change-case';
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
import CustomSelectBox from '../../../components/hook-form/CustomSelectBox';
import { generateEnumArray } from '../../../utils/formatEnum';
import { CrawlerCategoryEnum } from '../enums/crawlerCategoryEnum';
import CustomAutoComplete from '../../../components/hook-form/CustomAutoComplete';
import crawlerConfigService from '../../crawlerConfig/services/crawlerConfigService';
import crawlerCategoryService from '../services/CrawlerCategoryService';
import { checkArrayHasIndex } from '../../../utils/helper';
import { setDate } from 'date-fns/esm';

const EditCrawlerCategory = () => {
  const navigate = useNavigate();
  const [crawlConfigs, setCrawlConfigs] = useState({});
  const { id } = useParams();
  const [data, setData] = useState({});

  const RegisterSchema = Yup.object().shape({
    name: Yup.string().required('Name required'),
    link: Yup.string().required('Base URL required'),
    status: Yup.number().required('Status is required'),
    crawlConfigId: Yup.number().required('Crawl Config ID is required'),
  });

  const defaultValues = {
    name: '',
    link: '',
    status: 0,
    crawlConfigId: '',
  };

  const methods = useForm({
    resolver: yupResolver(RegisterSchema),
    defaultValues,
  });

  useEffect(() => {
    getListCrawlConfig();
    getDetailsData();
  }, []);

  const getListCrawlConfig = async () => {
    await crawlerConfigService.getAll().then((res) => {
      setCrawlConfigs(res.data.data);
    });
  };

  const getDetailsData = async () => {
    await crawlerCategoryService.findById(id).then((res) => {
      console.log('🚀 ~ file: EditCrawlerCategory.jsx ~ line 65 ~ getDetailsData ~ res.data.data', res.data.data);
      setValue('name', res.data.data?.name);
      setValue('link', res.data.data?.link);
      setValue('status', res.data.data?.status);
      setValue('crawlConfigId', res.data.data?.crawlConfigId);
      setData(res.data.data);
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
      .catch((err) => {
        console.log('🚀 ~ file: EditCrawlerCategory.jsx ~ line 74 ~ onSubmit ~ err', err);
      });
  };

  return (
    <Page title="Crawl Categories">
      <Container maxWidth="xl">
        <Stack direction="row" alignItems="center" justifyContent="space-between" mb={5}>
          <Typography variant="h4" gutterBottom>
            Edit Crawler Category
          </Typography>
          <Button
            variant="contained"
            component={RouterLink}
            to={ROUTES.CRAWLER_CATEGORY}
            startIcon={<Iconify icon="eva:plus-fill" />}
          >
            Back
          </Button>
        </Stack>
        <Card sx={{ padding: '2rem' }}>
          <FormProvider methods={methods} onSubmit={handleSubmit(onSubmit)}>
            <Stack spacing={3}>
              <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
                <RHFTextField name="name" label="Name" />
                <RHFTextField name="link" label="Link" />
              </Stack>
              <CustomAutoComplete
                name="crawlConfigId"
                label="Crawl Config ID"
                options={crawlConfigs?.content}
                setOptionLabel={(option) => option.name}
                defaultValue={data?.crawlConfig}
              />
              <CustomSelectBox
                enumType={CrawlerCategoryEnum}
                name="status"
                label="Status"
                firstOptionLabel="Choose status"
              />
              <LoadingButton fullWidth size="large" type="submit" variant="contained" loading={isSubmitting}>
                Submit
              </LoadingButton>
            </Stack>
          </FormProvider>
        </Card>
      </Container>
    </Page>
  );
};

export default EditCrawlerCategory;
