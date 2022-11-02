import React from 'react';
import { useFormContext, Controller } from 'react-hook-form';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';

const CustomCheckBox = ({ name, label }) => {
  const { control } = useFormContext();

  return (
    <Controller
      name={name}
      control={control}
      render={({ field: { onChange, value }, fieldState: { error } }) => (
        <FormControlLabel control={<Checkbox 
          checked={value == 1}
          onChange={(ev) => onChange(ev.target.checked ? 1 : 0)} />} 
          label={label} />
      )}
    />
  );
};

export default CustomCheckBox;
