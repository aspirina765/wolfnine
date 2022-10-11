import { NextPage } from 'next';
import React, { useState, useLayoutEffect } from 'react';
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
import { useForm } from 'react-hook-form';
import { MessageType } from '../../utils/types';
import authService from '../../modules/auth/services/authService';
import { CustomMessageType, LoginStatus } from '../../entities/enums';
import { useRouter } from 'next/router';
import AlertCustom from '../../modules/shared/AlertCustom';
import { ROUTES } from '../../configs/router';
import { signIn, useSession } from 'next-auth/react';
import { AUTH_PROVIDER } from '../../constants/auth';
import { NextAuthStatus } from '../../modules/auth/enums';

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

export type LoginDataType = {
    username: string;
    password: string;
};

const Login: NextPage = () => {
    const { register, handleSubmit, formState: { errors }, reset } = useForm<LoginDataType>();
    const [message, setMessage] = useState<MessageType>();
    const [loginStatus, setLoginStatus] = useState<LoginStatus>();
    const router = useRouter();
    const { status } = useSession();

    const handleSubmitLogin = async (data: LoginDataType) => {

        const res = await signIn('credentials', { ...data, redirect: false });
        if (res?.status == 200) {
            return router.push(ROUTES.DASHBOARD);
        } else {
            setMessage({
                title: 'Error',
                type: CustomMessageType.ERROR,
                message: 'Login failed'
            });
            setLoginStatus(LoginStatus.FAILED);
        }
    };

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs" >
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
                        Sign in
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit(handleSubmitLogin)} noValidate sx={{ mt: 1 }}>
                        {
                            loginStatus === LoginStatus.FAILED &&
                            <AlertCustom title={message?.title} type={message?.type} message={message?.message} />
                        }
                        <TextField
                            margin="normal"
                            fullWidth
                            id="username"
                            label="Username"
                            autoComplete="username"
                            autoFocus
                            error={errors.username != null}
                            helperText={errors.username?.message?.toString()}
                            {...register('username', { required: 'Username is required' })}
                        />
                        <TextField
                            margin="normal"
                            fullWidth
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            error={errors.password != null}
                            helperText={errors.password?.message?.toString()}
                            {...register('password', {
                                required: 'Password is required', minLength: {
                                    value: 6,
                                    message: 'Password must be at least 6 characters'
                                }
                            })}
                        />
                        <FormControlLabel
                            control={<Checkbox value="remember" color="primary" />}
                            label="Remember me"
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                        >
                            Sign In
                        </Button>
                        <Grid container>
                            <Grid item xs>
                                <Link href="#" variant="body2">
                                    Forgot password?
                                </Link>
                            </Grid>
                            <Grid item>
                                <Link href={ROUTES.REGISTER} variant="body2">
                                    {"Don't have an account? Sign Up"}
                                </Link>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
                <Copyright sx={{ mt: 8, mb: 4 }} />
            </Container>
        </ThemeProvider>
    );
};

export default Login;