import { Chip, Divider, FormHelperText, Stack } from '@mui/material';
import * as React from 'react';
import { RHFCheckbox, RHFTextField } from '../../../components/hook-form';
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import { CrawlDataType, SelectorType } from '../types/crawlerConfigEnum';
import { ST } from '../types/crawlerConfigType';
import { generateEnumArray } from '../../../utils/formatEnum';
import Typography from '@mui/material/Typography';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import CustomSelectBox from '../../../components/hook-form/CustomSelectBox';

const ConfigSelectorFormItem = ({ config, index, onChangeConfigItem, watch, arrayName }: any) => {
    return (
        <>
            <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
                <Stack maxWidth={100} minWidth={50}>
                    <Chip label={index} color="primary" />
                </Stack>
            </Stack>

            <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
                <RHFTextField name={`${arrayName}[${index}].key`} label="Key" />
                <RHFTextField name={`${arrayName}[${index}].selector`} label="Selector Value" />
            </Stack>
            <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
                <CustomSelectBox
                    name={`${arrayName}[${index}].type`}
                    enumType={SelectorType}
                    label="Selector Type"
                    firstOptionLabel="Choose Type" />
            </Stack>
            {
                watch(`${arrayName}[${index}].type`) == SelectorType.GET_ATTRIBUTE && <RHFTextField name={`${arrayName}[${index}].attribute`} label="Attribute" />
            }
            <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
                <CustomSelectBox
                    name={`${arrayName}[${index}].dataType`}
                    enumType={CrawlDataType}
                    label="Data Type"
                    firstOptionLabel="Choose Type" />
            </Stack>
            <FormGroup row>
                <RHFCheckbox name={`${arrayName}[${index}].isLink`} label="Is URL" />
                <RHFCheckbox name={`${arrayName}[${index}].isArray`} label="Is List" />
            </FormGroup>
            <Divider />
        </>
    );
};

export default ConfigSelectorFormItem;