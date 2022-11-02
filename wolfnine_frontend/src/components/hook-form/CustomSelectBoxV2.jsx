import React from 'react';
// form
import { useFormContext, Controller } from 'react-hook-form';
// @mui
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import { generateEnumArray } from '../../utils/formatEnum';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import { FormHelperText } from '@mui/material';

const CustomSelectBoxV2 = ({ items, itemKey, itemLabel, name, label, firstOptionLabel, ...other }) => {
  const { control } = useFormContext();

  return (
    <Controller
      name={name}
      control={control}
      render={({ field, fieldState: { error } }) => (
        <FormControl fullWidth error={!!error}>
          <InputLabel id={name}>{label}</InputLabel>
          <Select
            labelId={name}
            id={name}
            {...field}
            value={String(field.value)}
            name={name}
            label={label}
            {...other}
          >
            <MenuItem value={0}>{firstOptionLabel}</MenuItem>
            {items.map((item, index) => (
              <MenuItem value={item[itemKey]} key={index}>
                  {item[itemLabel]}
              </MenuItem>
            ))}
          </Select>
          {error && <FormHelperText>{error?.message}</FormHelperText>}
        </FormControl>
      )}
    />
  );
};

export default CustomSelectBoxV2;
