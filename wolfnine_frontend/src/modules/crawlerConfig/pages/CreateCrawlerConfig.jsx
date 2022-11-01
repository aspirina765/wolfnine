import React, { useEffect } from 'react';
import Page from '../../../components/Page';
import { filter } from 'lodash';
import { sentenceCase } from 'change-case';
import { useState } from 'react';
import { Link as RouterLink, Navigate, useLocation } from 'react-router-dom';
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

import ListSubheader from '@mui/material/ListSubheader';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Collapse from '@mui/material/Collapse';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import StarBorder from '@mui/icons-material/StarBorder';
import ConfigSelectorFormItem from '../components/ConfigSelectorFormItem';
import { SelectorType } from '../types/crawlerConfigEnum';
import { generateUUID } from '../../../utils/generate';
import crawlerConfigService from '../services/crawlerConfigService';

let selectorConfigInitial = {
  id: generateUUID(),
  key: '',
  selector: '',
  type: 0,
  attribute: '',
  dataType: 0,
  isArray: 0,
  isLink: 0,
};

const CreateCrawlerConfig = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [selectorConfigs, setSelectorConfigs] = useState([]);
  const [selectorDetailsConfigs, setSelectorDetailsConfigs] = useState([]);
  const [openSelectorConfig, setOpenSelectorConfig] = React.useState(true);
  const [openSelectorDetailsConfig, setOpenSelectorDetailsConfig] = React.useState(true);
  const navigate = useNavigate();

  const RegisterSchema = Yup.object().shape({
    name: Yup.string().required('Name required'),
    baseUrl: Yup.string().required('Base URL required'),
    selectorList: Yup.string().required('Selector list is required'),
    selectors: Yup.array()
      .of(
        Yup.object().shape({
          key: Yup.string().required('Key is required'),
          selector: Yup.string().required('Selector is required'),
          type: Yup.number().required('Type is required').notOneOf([0, null], 'Type is required').default(0),
          attribute: Yup.string().when('type', {
            is: SelectorType.GET_ATTRIBUTE,
            then: Yup.string().required('Attribute must be required when type is get attribute'),
          }),
          dataType: Yup.number()
            .required('Data type is required')
            .notOneOf([0, null], 'Data type is required')
            .default(0),
          isLink: Yup.number().default(0),
          isArray: Yup.number().default(0),
        })
      )
      .required('Selector Config is required'),
    selectorDetails: Yup.array()
      .of(
        Yup.object().shape({
          key: Yup.string().required('Key is required'),
          selector: Yup.string().required('Selector is required'),
          type: Yup.number().required('Type is required').notOneOf([0, null], 'Type is required').default(0),
          attribute: Yup.string().when('type', {
            is: SelectorType.GET_ATTRIBUTE,
            then: Yup.string().required('Attribute must be required when type is get attribute'),
          }),
          dataType: Yup.number()
            .required('Data type is required')
            .notOneOf([0, null], 'Data type is required')
            .default(0),
          isLink: Yup.number().default(0),
          isArray: Yup.number().default(0),
        })
      )
      .required('Selector Details Config is required'),
  });

  const defaultValues = {
    name: '',
    baseUrl: '',
    selectorList: '',
    selectors: [],
    selectorDetails: [],
  };

  const methods = useForm({
    resolver: yupResolver(RegisterSchema),
    defaultValues,
  });

  const {
    handleSubmit,
    formState: { isSubmitting, errors },
    getValues,
    setValue,
    watch,
  } = methods;

  const onSubmit = async (data) => {
    console.log('🚀 ~ file: CreateCrawlerConfig.jsx ~ line 83 ~ onSubmit ~ data', data);
    await crawlerConfigService
      .create(data)
      .then((res) => {
        navigate(ROUTES.CRAWLER_CONFIGS, { replace: true });
      })
      .catch((err) => {
        console.log('🚀 ~ file: CreateCrawlerConfig.jsx ~ line 124 ~ onSubmit ~ err', err);
      });
  };

  const handleToggleSelectorConfigs = () => {
    setOpenSelectorConfig(!openSelectorConfig);
  };

  const handleToggleSelectorDetailsConfigs = () => {
    setOpenSelectorDetailsConfig(!openSelectorDetailsConfig);
  };

  const handleAddNewSelectorConfig = () => {
    setValue('selectors', [...getValues('selectors'), { ...selectorConfigInitial, id: generateUUID() }]);
  };

  const handleAddNewSelectorDetailsConfig = () => {
    setValue('selectorDetails', [...getValues('selectorDetails'), { ...selectorConfigInitial, id: generateUUID() }]);
  };

  const handleChangeSelectorConfigItem = (data) => {
    console.log('🚀 ~ file: CreateCrawlerConfig.jsx ~ line 96 ~ handleChangeSelectorConfigItem ~ data', data);
  };

  const handleRemoveSelectorConfigItem = (id) => {
    let configs = getValues('selectors');
    configs = configs.filter((item) => {
      return item.id !== id
    })
    console.log('check', configs);
    setValue('selectors', configs);
  };

  const handleRemoveSelectorDetailsConfigItem = (id) => {
    let configs = getValues('selectorDetails');
    configs = configs.filter((item) => item.id !== id);
    setValue('selectorDetails', configs);
  };
  return (
    <Page title="Crawl Config">
      <Container maxWidth="xl">
        <Stack direction="row" alignItems="center" justifyContent="space-between" mb={5}>
          <Typography variant="h4" gutterBottom>
            Create Crawler Configs
          </Typography>
          <Button
            variant="contained"
            component={RouterLink}
            to={ROUTES.CRAWLER_CONFIGS}
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
                <RHFTextField name="baseUrl" label="Base URL" />
              </Stack>
              <RHFTextField name="selectorList" label="Selector List" />
              <List
                sx={{ width: '100%', bgcolor: 'background.paper' }}
                component="nav"
                aria-labelledby="nested-list-subheader"
                subheader={
                  <ListSubheader component="div" id="nested-list-subheader">
                    Selector Config
                  </ListSubheader>
                }
              >
                <ListItemButton onClick={handleToggleSelectorConfigs}>
                  <ListItemIcon>
                    <InboxIcon />
                  </ListItemIcon>
                  <ListItemText primary="Show" />
                  {openSelectorConfig ? <ExpandLess /> : <ExpandMore />}
                </ListItemButton>
                <Collapse in={openSelectorConfig} timeout="auto" unmountOnExit>
                  <Stack padding={5}>
                    <Stack spacing={2}>
                      {watch('selectors').map((config, index) => (
                        <ConfigSelectorFormItem
                          key={index}
                          config={config}
                          index={index}
                          onChangeConfigItem={handleChangeSelectorConfigItem}
                          watch={watch}
                          arrayName="selectors"
                          onRemoveConfigItem={handleRemoveSelectorConfigItem}
                        />
                      ))}
                    </Stack>
                  </Stack>
                  <Button onClick={() => handleAddNewSelectorConfig()} variant="contained" color="primary">
                    Add New
                  </Button>
                </Collapse>
              </List>
              <List
                sx={{ width: '100%', bgcolor: 'background.paper' }}
                component="nav"
                aria-labelledby="selectorDetailsConfig"
                subheader={
                  <ListSubheader component="div" id="selectorDetailsConfig">
                    Selector Details Config
                  </ListSubheader>
                }
              >
                <ListItemButton onClick={handleToggleSelectorDetailsConfigs}>
                  <ListItemIcon>
                    <InboxIcon />
                  </ListItemIcon>
                  <ListItemText primary="Show" />
                  {openSelectorDetailsConfig ? <ExpandLess /> : <ExpandMore />}
                </ListItemButton>
                <Collapse in={openSelectorDetailsConfig} timeout="auto" unmountOnExit>
                  <Stack padding={5}>
                    <Stack spacing={2}>
                      {watch('selectorDetails').map((config, index) => (
                        <ConfigSelectorFormItem
                          key={index}
                          config={config}
                          index={index}
                          onChangeConfigItem={handleChangeSelectorConfigItem}
                          watch={watch}
                          arrayName="selectorDetails"
                          onRemoveConfigItem={handleRemoveSelectorDetailsConfigItem}
                        />
                      ))}
                    </Stack>
                  </Stack>
                  <Button onClick={() => handleAddNewSelectorDetailsConfig()} variant="contained" color="primary">
                    Add New
                  </Button>
                </Collapse>
              </List>
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

export default CreateCrawlerConfig;
