import PropTypes from 'prop-types';
// material
import { styled } from '@mui/material/styles';
import { Toolbar, Tooltip, IconButton, Typography, OutlinedInput, InputAdornment, Stack, Button } from '@mui/material';
import Iconify from '../../../components/Iconify';
import SendIcon from '@mui/icons-material/Send';
import { useState } from 'react';
import PushToShopeeShopForm from './PushToShopeeShopForm';
import PushToApiForm from './PushToApiForm';
// component

// ----------------------------------------------------------------------

const RootStyle = styled(Toolbar)(({ theme }) => ({
  height: 96,
  display: 'flex',
  justifyContent: 'space-between',
  padding: theme.spacing(0, 1, 0, 3),
}));

const SearchStyle = styled(OutlinedInput)(({ theme }) => ({
  width: 240,
  transition: theme.transitions.create(['box-shadow', 'width'], {
    easing: theme.transitions.easing.easeInOut,
    duration: theme.transitions.duration.shorter,
  }),
  '&.Mui-focused': { width: 320, boxShadow: theme.customShadows.z8 },
  '& fieldset': {
    borderWidth: `1px !important`,
    borderColor: `${theme.palette.grey[500_32]} !important`,
  },
}));

// ----------------------------------------------------------------------

ProductListToolbar.propTypes = {
  numSelected: PropTypes.number,
  filterName: PropTypes.string,
  onFilterName: PropTypes.func,
  onDelete: PropTypes.func,
};

export default function ProductListToolbar({ numSelected, filterName, onFilterName, onDelete, productIds }) {
  const [openPushShopeeForm, setOpenPushShopeeForm] = useState(false);
  const [openPushApiForm, setOpenPushApiForm] = useState(false);

  const handleOpenPushShopeeForm = () => {
    setOpenPushShopeeForm(true);
  };

  const handleClosePushShopeeForm = () => {
    setOpenPushShopeeForm(false);
  };

  const handleToggleOpenPushApiForm = (action) => {
    setOpenPushApiForm(action);
  };

  return (
    <RootStyle
      sx={{
        ...(numSelected > 0 && {
          color: 'primary.main',
          bgcolor: 'primary.lighter',
        }),
      }}
    >
      {numSelected > 0 ? (
        <Typography component="div" variant="subtitle1">
          {numSelected} selected
        </Typography>
      ) : (
        <SearchStyle
          value={filterName}
          onChange={onFilterName}
          placeholder="Search user..."
          startAdornment={
            <InputAdornment position="start">
              <Iconify icon="eva:search-fill" sx={{ color: 'text.disabled', width: 20, height: 20 }} />
            </InputAdornment>
          }
        />
      )}

      {numSelected > 0 ? (
        <Stack spacing={2} direction="row">
          <Button onClick={() => handleToggleOpenPushApiForm(true)} variant="contained" startIcon={<SendIcon />} color="success">
            Push To API
          </Button>
          <Button onClick={handleOpenPushShopeeForm} variant="contained" startIcon={<SendIcon />} color="warning">
            Push To Shopee
          </Button>
          <Tooltip title="Delete" onClick={onDelete}>
            <IconButton>
              <Iconify icon="eva:trash-2-fill" />
            </IconButton>
          </Tooltip>
        </Stack>
      ) : (
        <Tooltip title="Filter list">
          <IconButton>
            <Iconify icon="ic:round-filter-list" />
          </IconButton>
        </Tooltip>
      )}
      <PushToShopeeShopForm
        open={openPushShopeeForm}
        onClose={handleClosePushShopeeForm}
        numSelected={numSelected}
        productIds={productIds}
      />
      <PushToApiForm
        open={openPushApiForm}
        onClose={() => handleToggleOpenPushApiForm(false)}
        numSelected={numSelected}
        productIds={productIds}
      />
    </RootStyle>
  );
}
