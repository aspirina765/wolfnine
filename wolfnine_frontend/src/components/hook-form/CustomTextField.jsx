import PropTypes from 'prop-types';
// form
import { useFormContext, Controller } from 'react-hook-form';
// @mui
import { TextField } from '@mui/material';

// ----------------------------------------------------------------------

CustomTextField.propTypes = {
  name: PropTypes.string,
};

export default function CustomTextField({ name, watch, ...other }) {
  const { control } = useFormContext();

  return (
    <Controller
      name={name}
      control={control}
      render={({ field, fieldState: { error } }) => (
        <TextField
          {...field}
          fullWidth
          value={typeof field.value === 'number' && field.value === 0 ? '' : watch(name)}
          error={!!error}
          helperText={error?.message}
          {...other}
        />
      )}
    />
  );
}
