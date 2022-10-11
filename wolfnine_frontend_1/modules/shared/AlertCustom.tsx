import React from 'react';
import Alert from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';
import { MessageType } from '../../utils/types';
import { useState, useEffect } from 'react';

const AlertCustom = ({ type, title, message }: MessageType) => {
    const [show, setShow] = useState(true);
    // useEffect(() => {
    //     const timerId = setTimeout(() => {
    //         setShow(false);
    //     }, 5000)
    //     return () => clearTimeout(timerId);
    // }, [])
    return (
        <>
            {show && <Alert variant="filled" severity={type}>
                <AlertTitle>{title}</AlertTitle>
                <strong>{message}</strong>
            </Alert>
            }
        </>
    );
};

export default AlertCustom;