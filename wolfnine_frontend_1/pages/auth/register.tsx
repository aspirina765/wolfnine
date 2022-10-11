import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { useForm, useWatch } from 'react-hook-form';
import { RULES } from '../../constants/validation';
import { useRef, useState } from 'react';
import authService from '../../modules/auth/services/authService';
import AlertCustom from '../../modules/shared/AlertCustom';
import { MessageType } from '../../utils/types';
import { CustomMessageType } from '../../entities/enums';
import { useRouter } from 'next/router';
import { ROUTES } from '../../configs/router';


function Copyright(props: any) {
    return (
        <Typography variant="body2" color="text.secondary" align="center" {...props}>
            {'Copyright © '}
            <Link color="inherit" href="https://mui.com/">
                Your Website
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

const theme = createTheme();

export type RegisterData = {
    fullName: string,
    email: string,
    phone: string,
    username: string,
    password: string,
    confirmPassword: string
}

export default function SignUp() {
    const { register, handleSubmit, formState: { errors }, watch, reset } = useForm<RegisterData>();
    const [registerSuccess, setRegisterSuccess] = useState(false);
    const [message, setMessage] = useState<MessageType>();

    const handleSubmitForm = async (data: RegisterData) => {
        await authService.register(data)
            .then(res => {
                setMessage({
                    title: 'Success',
                    type: CustomMessageType.SUCCESS,
                    message: 'Register successfully !'
                });
                setRegisterSuccess(true);
                reset();
            })
            .catch(err => {

            });
    };

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign up
                    </Typography>
                    <Box component="form" noValidate onSubmit={handleSubmit(handleSubmitForm)} sx={{ mt: 3 }}>
                        {
                            registerSuccess && <Grid spacing={2} marginBottom={2}>
                                <AlertCustom title={message?.title} type={message?.type} message={message?.message} />
                            </Grid>
                        }
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    autoComplete="given-name"
                                    required
                                    fullWidth
                                    id="fullName"
                                    label="Fullname"
                                    autoFocus
                                    error={errors.fullName != null}
                                    helperText={errors.fullName?.message?.toString()}
                                    {...register('fullName', { required: 'Fullname is required' })}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    fullWidth
                                    id="email"
                                    label="Email"
                                    autoComplete="family-name"
                                    error={errors.email != null}
                                    helperText={errors.email?.message?.toString()}
                                    {...register('email', {
                                        required: 'Email is required',
                                        pattern: {
                                            value: RULES.REGEX_EMAIL,
                                            message: "Invalid email address"
                                        }
                                    })}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    id="phone"
                                    label="Phone Number"
                                    autoComplete="phone"
                                    error={errors.phone != null}
                                    helperText={errors.phone?.message?.toString()}
                                    {...register('phone', {
                                        required: 'Phone number is required',
                                        pattern: {
                                            value: RULES.REGEX_PHONE,
                                            message: 'Phone number invalid'
                                        }
                                    })}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    label="Username"
                                    id="username"
                                    autoComplete="username"
                                    error={errors.username != null}
                                    helperText={errors.username?.message?.toString()}
                                    {...register('username', { required: 'Username is required' })}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    label="Password"
                                    type="password"
                                    id="password"
                                    autoComplete="new-password"
                                    error={errors.password != null}
                                    helperText={errors.password?.message?.toString()}
                                    {...register('password', {
                                        required: 'password is required',
                                        minLength: {
                                            value: RULES.PASSWORD_MIN_LENGTH, message: 'Password must be at least 6 characters'
                                        }
                                    })}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    label="Confirm Password"
                                    type="password"
                                    id="confirmPassword"
                                    autoComplete="new-confirmPassword"
                                    error={errors.confirmPassword != null}
                                    helperText={errors.confirmPassword?.message?.toString()}
                                    {...register('confirmPassword', {
                                        required: 'Confirm Password is required',
                                        minLength: { value: RULES.PASSWORD_MIN_LENGTH, message: 'Password must be at least 6 characters' },
                                        validate: (value) =>
                                            value === watch('password') || 'The password not match'
                                    })}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <FormControlLabel
                                    control={<Checkbox value="allowExtraEmails" color="primary" />}
                                    label="I want to receive inspiration, marketing promotions and updates via email."
                                />
                            </Grid>
                        </Grid>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                        >
                            Sign Up
                        </Button>
                        <Grid container justifyContent="flex-end">
                            <Grid item>
                                <Link href={ROUTES.LOGIN} variant="body2">
                                    Already have an account? Sign in
                                </Link>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
                <Copyright sx={{ mt: 5 }} />
            </Container>
        </ThemeProvider>
    );
}
