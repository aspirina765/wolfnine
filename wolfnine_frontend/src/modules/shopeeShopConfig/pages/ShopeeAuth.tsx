import { Dialog } from '@mui/material';
import * as React from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { ROUTES } from '../../../constants/routerConfig';
import shopeeShopConfigService from '../services/shopeeShopConfigService';
import Backdrop from '@mui/material/Backdrop';
import CircularProgress from '@mui/material/CircularProgress';
import Button from '@mui/material/Button';

const ShopeeAuth = () => {
    const navigate = useNavigate();
    const [searchParams, setSearchParams] = useSearchParams();
    const [openDiaglog, setOpenDiaglog] = React.useState(true);

    React.useEffect(() => {
        handleShopCode();
    }, [])

    const handleShopCode = async () => {
        const code = searchParams.get('code');
        console.log("🚀 ~ file: ShopeeAuth.tsx ~ line 21 ~ handleShopCode ~ code", code)
        const shopId = parseFloat(searchParams.get('shop_id') ?? '0');
        console.log("🚀 ~ file: ShopeeAuth.tsx ~ line 23 ~ handleShopCode ~ shopId", shopId)
        if (code == '' || shopId == 0 || code == null || shopId == null) {
            navigate(ROUTES.SHOPEE_SHOP_CONFIG);
            return;
        }
        await shopeeShopConfigService.saveConfig({
            code,
            shopId
        }).then(res => {
            navigate(ROUTES.SHOPEE_SHOP_CONFIG);
        })
    }

    return (
        <>
            <Backdrop
                sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1, background: 'rgba(0, 0, 0, 0.5)' }}
                open={openDiaglog}
                onClick={() => { }}
            >
                <CircularProgress color="inherit" />
            </Backdrop>
        </>
    );
};

export default ShopeeAuth;