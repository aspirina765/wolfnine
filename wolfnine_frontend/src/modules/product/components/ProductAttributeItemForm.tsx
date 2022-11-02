import { Chip, Divider, FormHelperText, Stack, Button } from '@mui/material';
import * as React from 'react';
import { RHFCheckbox, RHFTextField } from '../../../components/hook-form';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import { generateEnumArray } from '../../../utils/formatEnum';
import Typography from '@mui/material/Typography';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import CustomSelectBox from '../../../components/hook-form/CustomSelectBox';
import CustomCheckBox from '../../../components/hook-form/CustomCheckBox';
import Iconify from '../../../components/Iconify';
import CustomTextField from '../../../components/hook-form/CustomTextField';
import { CrawlDataType, SelectorType } from '../../crawlerConfig/types/crawlerConfigEnum';

const ProductAttributeItemForm = ({ config, index, onChangeConfigItem, watch, arrayName, onRemoveConfigItem }: any) => {
    return (
        <>
            <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
                <Stack maxWidth={100} minWidth={50}>
                    <Chip label={index} color="primary" />
                </Stack>
                <Button
                    variant="contained"
                    color='error'
                    startIcon={<Iconify icon="fluent:delete-20-filled" sx="" />}
                    onClick={() => onRemoveConfigItem(config.id)}
                >
                    Delete
                </Button>
            </Stack>

            <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
                <CustomTextField name={`${arrayName}[${index}].key`} label="Key" watch={watch} InputProps={{
                    readOnly: true,
                }} />
                <CustomTextField name={`${arrayName}[${index}].value`} label="Value" watch={watch} InputProps={{
                    readOnly: true,
                }} />
            </Stack>
            <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
                <CustomSelectBox
                    name={`${arrayName}[${index}].type`}
                    enumType={CrawlDataType}
                    label="Data Type"
                    firstOptionLabel="Choose Type"
                    readOnly={true}
                />
            </Stack>
            <Divider />
        </>
    );
};

export default ProductAttributeItemForm;