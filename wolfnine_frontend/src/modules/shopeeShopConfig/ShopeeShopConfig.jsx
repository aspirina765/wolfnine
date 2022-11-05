import React, { useEffect } from 'react';
import { useState } from 'react';
import { Link as RouterLink, useLocation, useNavigate } from 'react-router-dom';
// material
import {
  Card,
  Table,
  Stack,
  Avatar,
  Button,
  Checkbox,
  TableRow,
  TableBody,
  TableCell,
  Container,
  Typography,
  TableContainer,
  TablePagination,
} from '@mui/material';
// components
import Page from '../../components/Page';
import Label from '../../components/Label';
import Scrollbar from '../../components/Scrollbar';
import Iconify from '../../components/Iconify';
import SearchNotFound from '../../components/SearchNotFound';
// mock
import USERLIST from '../../_mock/user';
import TableHeadCustom from '../../sections/shared/TableHeadCustom';
import { UserMoreMenu } from '../../sections/@dashboard/user';
import MoreMenuCustom from '../../sections/shared/MoreMenuCustom';
import { fDateTime } from '../../utils/formatTime';
import { ROUTES } from '../../constants/routerConfig';
import { generateRouteWithParam } from '../../utils/generate';
import shopeeShopConfigService from './services/shopeeShopConfigService';
import TableLoading from '../shared/components/TableLoading';
import ShopeeShopConfigToolbar from './components/ShopeeShopConfigToolbar';

// ----------------------------------------------------------------------

const TABLE_HEAD = [
  { id: 'id', label: 'Id', alignRight: false },
  { id: 'name', label: 'Name', alignRight: false },
  { id: 'shopId', label: 'Shop Id', alignRight: false },
  { id: 'expiredAt', label: 'Expired At', alignRight: false },
  { id: 'createdAt', label: 'Created At', alignRight: false },
  { id: '' },
];

// ----------------------------------------------------------------------

const ShopeeShopConfig = () => {
  const [page, setPage] = useState(0);
  const [order, setOrder] = useState('asc');
  const [selected, setSelected] = useState([]);
  const [filterName, setFilterName] = useState('');
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [orderBy, setOrderBy] = useState('name');
  const [listData, setListData] = useState({});
  const location = useLocation();
  const query = new URLSearchParams(location.search);
  const navigate = useNavigate();

  useEffect(() => {
    getListData();
  }, [page, rowsPerPage]);

  const getListData = async () => {
    await shopeeShopConfigService
      .findAllByAuthUser({
        page,
        limit: rowsPerPage,
      })
      .then((res) => {
        setListData(res.data?.data);
      });
  };

  useEffect(() => {
    getListData();
  }, []);

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(property);
  };

  const handleSelectAllClick = (event) => {
    if (event.target.checked) {
      const newSelecteds = listData?.content?.map((n) => n.id);
      setSelected(newSelecteds);
      return;
    }
    setSelected([]);
  };

  const handleClick = (event, name) => {
    const selectedIndex = selected.indexOf(name);
    let newSelected = [];
    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, name);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(selected.slice(0, selectedIndex), selected.slice(selectedIndex + 1));
    }
    setSelected(newSelected);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleFilterByName = (event) => {
    setFilterName(event.target.value);
  };

  const handleDeleteItem = async (item) => {};

  const handleCreateNewConfig = async () => {
    await shopeeShopConfigService.generateShopAuthUrl().then((res) => {
      window.location.replace(res.data?.data, { replace: true });
    });
  };

  return (
    <Page title="Crawl Config">
      <Container maxWidth="xl">
        <Stack direction="row" alignItems="center" justifyContent="space-between" mb={5}>
          <Typography variant="h4" gutterBottom>
            Shopee Shop Config
          </Typography>
          <Button
            variant="contained"
            // component={RouterLink}
            // to={ROUTES.CREATE_CRAWLER_CATEGORY}
            onClick={handleCreateNewConfig}
            startIcon={<Iconify icon="eva:plus-fill" />}
          >
            New Config
          </Button>
        </Stack>
        <Card>
          <ShopeeShopConfigToolbar numSelected={selected?.length} />
          <Scrollbar>
            <TableContainer sx={{ minWidth: 800 }}>
              <Table>
                <TableHeadCustom
                  order={order}
                  orderBy={orderBy}
                  headLabel={TABLE_HEAD}
                  rowCount={listData?.content?.length}
                  numSelected={selected.length}
                  onRequestSort={handleRequestSort}
                  onSelectAllClick={handleSelectAllClick}
                />
                <TableBody>
                  {!listData?.content && <TableLoading />}

                  {listData?.content?.map((config, index) => (
                    <TableRow hover key={index} tabIndex={-1} role="checkbox" selected={false} aria-checked={false}>
                      <TableCell padding="checkbox">
                        <Checkbox
                          checked={selected.indexOf(config.id) !== -1}
                          onChange={(event) => handleClick(event, config?.id)}
                        />
                      </TableCell>
                      <TableCell align="left">{config.id}</TableCell>
                      <TableCell component="th" scope="row">
                        <Stack direction="row" alignItems="center" spacing={2}>
                          <Typography variant="subtitle2" noWrap>
                            {config.name}
                          </Typography>
                        </Stack>
                      </TableCell>
                      <TableCell align="left">{config.shopId}</TableCell>
                      <TableCell align="left">{config.expiredAt}</TableCell>
                      <TableCell align="left">{config.createdAt}</TableCell>
                      <TableCell align="right">
                        <MoreMenuCustom
                          item={config}
                          onDeleteItem={handleDeleteItem}
                          editLink={generateRouteWithParam(ROUTES.EDIT_CRAWLER_CATEGORY, ':id', config.id)}
                        />
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
              <TablePagination
                rowsPerPageOptions={[5, 10, 25]}
                component="div"
                count={listData.totalElements ?? 0}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
              />
            </TableContainer>
          </Scrollbar>
        </Card>
      </Container>
    </Page>
  );
};

export default ShopeeShopConfig;
