import React from 'react';
import { useFormContext, Controller } from 'react-hook-form';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import CircularProgress from '@mui/material/CircularProgress';

const CustomAutoComplete = ({ options, name, label, setOptionLabel, defaultValue }) => {
  const { control } = useFormContext();
  const [open, setOpen] = React.useState(false);
  const loading = open && options.length === 0;

  const getItemById = (id) => {
    if (options?.length > 0) {
      const index = options.findIndex((item) => item.id == id);
      return options[index];
    }
  };

  return (
    <Controller
      name={name}
      control={control}
      render={({ field: { onChange, value }, fieldState: { error } }) => (
        <Autocomplete
          open={open}
          onOpen={() => {
            setOpen(true);
          }}
          onClose={() => {
            setOpen(false);
          }}
          value={getItemById(value)}
          getOptionLabel={setOptionLabel}
          options={options}
          loading={loading}
          onChange={(event, newValue) => onChange(newValue.id)}
          renderInput={(params) => (
            <TextField
              {...params}
              label={label}
              InputProps={{
                ...params.InputProps,
                endAdornment: (
                  <React.Fragment>
                    {loading ? <CircularProgress color="inherit" size={20} /> : null}
                    {params.InputProps.endAdornment}
                  </React.Fragment>
                ),
              }}
              error={!!error}
              helperText={error?.message}
            />
          )}
        />
      )}
    />
  );
};

export default CustomAutoComplete;
