import React, { useEffect } from 'react';
import { filter } from 'lodash';
import { sentenceCase } from 'change-case';
import { useState } from 'react';
import { Link as RouterLink, useLocation } from 'react-router-dom';
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
import crawlerConfigService from './services/crawlerConfigService';
import { fDateTime } from '../../utils/formatTime';
import { ROUTES } from '../../constants/routerConfig';

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function getComparator(order, orderBy) {
  return order === 'desc'
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function applySortFilter(array, comparator, query) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  if (query) {
    return filter(array, (_user) => _user.name.toLowerCase().indexOf(query.toLowerCase()) !== -1);
  }
  return stabilizedThis.map((el) => el[0]);
}

// ----------------------------------------------------------------------

const TABLE_HEAD = [
  { id: 'name', label: 'Name', alignRight: false },
  { id: 'baseUrl', label: 'Base Url', alignRight: false },
  { id: 'status', label: 'Status', alignRight: false },
  { id: 'createdAt', label: 'Created At', alignRight: false },
  { id: '' },
];

// ----------------------------------------------------------------------

function CrawlerConfig() {
  const [page, setPage] = useState(0);
  const [order, setOrder] = useState('asc');
  const [selected, setSelected] = useState([]);
  const [filterName, setFilterName] = useState('');
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [orderBy, setOrderBy] = useState('name');
  const [crawlerConfigList, setCrawlerConfigList] = useState({});
  const location = useLocation();
  const query = new URLSearchParams(location.search);

  useEffect(() => {
    getListCrawlerConfig();
  }, [page, rowsPerPage]);

  const getListCrawlerConfig = async () => {
    await crawlerConfigService
      .getAll({
        page,
        limit: rowsPerPage,
        sortBy: 'createdAt',
      })
      .then((res) => {
        setCrawlerConfigList(res.data.data);
      });
  };

  useEffect(() => {
    getListCrawlerConfig();
  }, []);

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(property);
  };

  const handleSelectAllClick = (event) => {
    if (event.target.checked) {
      const newSelecteds = USERLIST.map((n) => n.name);
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

  return (
    <Page title="Crawl Config">
      <Container maxWidth="xl">
        <Stack direction="row" alignItems="center" justifyContent="space-between" mb={5}>
          <Typography variant="h4" gutterBottom>
            Crawler Configs
          </Typography>
          <Button
            variant="contained"
            component={RouterLink}
            to={ROUTES.CREATE_CRAWLER_CONFIG}
            startIcon={<Iconify icon="eva:plus-fill" />}
          >
            New Config
          </Button>
        </Stack>
        <Card>
          <Scrollbar>
            <TableContainer sx={{ minWidth: 800 }}>
              <Table>
                <TableHeadCustom
                  order={order}
                  orderBy={orderBy}
                  headLabel={TABLE_HEAD}
                  rowCount={crawlerConfigList.length}
                  numSelected={selected.length}
                  onRequestSort={handleRequestSort}
                  onSelectAllClick={handleSelectAllClick}
                />
                <TableBody>
                  {crawlerConfigList?.content?.map((config, index) => (
                    <TableRow hover key={index} tabIndex={-1} role="checkbox" selected={false} aria-checked={false}>
                      <TableCell padding="checkbox">
                        <Checkbox checked={false} onChange={(event) => handleClick(event, 'Hello')} />
                      </TableCell>
                      <TableCell component="th" scope="row" padding="none">
                        <Stack direction="row" alignItems="center" spacing={2}>
                          <Avatar alt={config.name} src={''} />
                          <Typography variant="subtitle2" noWrap>
                            {config.name}
                          </Typography>
                        </Stack>
                      </TableCell>
                      <TableCell align="left">{config.baseUrl}</TableCell>
                      <TableCell align="left">
                        <Label variant="ghost" color={(config.status === 'banned' && 'error') || 'success'}>
                          {config.status}
                        </Label>
                      </TableCell>
                      <TableCell align="left">{fDateTime(config.createdAt)}</TableCell>
                      <TableCell align="right">
                        <MoreMenuCustom />
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
              <TablePagination
                rowsPerPageOptions={[5, 10, 25]}
                component="div"
                count={crawlerConfigList.totalElements ?? 0}
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
}

export default CrawlerConfig;
