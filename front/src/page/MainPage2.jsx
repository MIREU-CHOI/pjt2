import * as React from 'react';
import { styled, useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import Button from '@mui/material/Button';
import MuiDrawer from '@mui/material/Drawer';
import MuiAppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import PaymentIcon from '@mui/icons-material/Payment';
import MonetizationOnIcon from '@mui/icons-material/MonetizationOn';
import ManageSearchIcon from '@mui/icons-material/ManageSearch';
import {  NavLink, Outlet } from 'react-router-dom';
import { Form } from 'react-bootstrap';
import '../css/mainPage.css';

//////////////////////////////////////////////////////////////////////////////////////////////////

const MainPage2 = () => {
  
  const [state, setState] = React.useState({
    top: false,
    left: false,
    bottom: false,
    right: false,
  });

  const toggleDrawer = (anchor, open) => (event) => {
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
      return;
    }
    setState({ ...state, [anchor]: open });
  };

  const list = (anchor) => (
    <Box
      sx={{ width: anchor === 'top' || anchor === 'bottom' ? 'auto' : 250 }}
      role="presentation"
      onClick={toggleDrawer(anchor, false)}
      onKeyDown={toggleDrawer(anchor, false)}
    >
      <List>
          {menus.map((menu, index) => (
            <ListItem key={index} disablePadding sx={{ display: 'block' }} >
              {/* <NavLink to={menu.path} > */}
              <ListItemButton
                component={NavLink} to={menu.path}
                sx={{
                  minHeight: 48,
                  justifyContent: "open" ? 'initial' : 'center',
                  px: 2.5,
                }}
              >
                <ListItemIcon
                  sx={{
                    minWidth: 0,
                    mr: "open" ? 3 : 'auto',
                    justifyContent: 'center',
                  }}
                >
                  { menu.icon }
                </ListItemIcon>
                <ListItemText primary={menu.name} sx={{ opacity: "open" ? 1 : 0 }} />
              </ListItemButton>
              {/* </NavLink> */}
            </ListItem>
          ))}
        </List>
      <Divider />
    </Box>
  );

  // 사이드바 메뉴
  const menus = [
    {name : "머니 충전", path: "/main/charge", icon: <MonetizationOnIcon /> },
    {name : "머니 결제", path: "/main/payment", icon: <PaymentIcon /> },
    {name : "거래 내역", path: "/main/history", icon: <ManageSearchIcon /> },
  ]
  
  const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
  })(({ theme, open }) => ({
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
      marginLeft: drawerWidth,
      width: `calc(100% - ${drawerWidth}px)`,
      transition: theme.transitions.create(['width', 'margin'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.enteringScreen,
      }),
    }),
  }));
  const drawerWidth = 240;
  const theme = useTheme();
  const [open, setOpen] = React.useState(false);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };
  const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
  }));
  const Drawer = styled(MuiDrawer, { shouldForwardProp: (prop) => prop !== 'open' })(
    ({ theme, open }) => ({
      width: drawerWidth,
      flexShrink: 0,
      whiteSpace: 'nowrap',
      boxSizing: 'border-box',
      ...(open && {
        ...openedMixin(theme),
        '& .MuiDrawer-paper': openedMixin(theme),
      }),
      ...(!open && {
        ...closedMixin(theme),
        '& .MuiDrawer-paper': closedMixin(theme),
      }),
    }),
  );
  const openedMixin = (theme) => ({
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
    overflowX: 'hidden',
  });
  
  const closedMixin = (theme) => ({
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    overflowX: 'hidden',
    width: `calc(${theme.spacing(7)} + 1px)`,
    [theme.breakpoints.up('sm')]: {
      width: `calc(${theme.spacing(8)} + 1px)`,
    },
  });



//////////////////////////////////////////////////////////////////////////////////////////////////

  return (
    <div>
      {/* {['left', 'right', 'top', 'bottom'].map((anchor, idx) => ( */}
        <Box sx={{ display: 'flex' }}>
{/* #################### 헤드 ####################### */}
      <CssBaseline />
      <AppBar position="fixed" open={"open"} >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            sx={{
              marginRight: 5,
              ...("open" && { display: 'none' }),
            }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap component="div">
            E4. Pay
          </Typography>
        </Toolbar>
      </AppBar>
          {/* <Button onClick={toggleDrawer('left', true)}>left</Button> */}
{/* =================== 사이드 바 ======================== */}
          <div>
          <Drawer
            // anchor={anchor}
            variant="permanent"
            open={open}
            // onClose={toggleDrawer('left', false)}
            className="drawer"
          >
            <DrawerHeader>
              <IconButton onClick={handleDrawerClose}>
                {theme.direction === 'rtl' ? <ChevronRightIcon /> : <ChevronLeftIcon />}
              </IconButton>
            </DrawerHeader>
            <Divider />
            {/* ------------- 사이드 메뉴들1 --------------------- */}
        <List>
          {menus.map((menu, index) => (
            <ListItem key={index} disablePadding sx={{ display: 'block' }} >
              {/* <NavLink to={menu.path} > */}
              <ListItemButton
                component={NavLink} to={menu.path}
                sx={{
                  minHeight: 48,
                  justifyContent: open ? 'initial' : 'center',
                  px: 2.5,
                }}
              >
                <ListItemIcon
                  sx={{
                    minWidth: 0,
                    mr: open ? 3 : 'auto',
                    justifyContent: 'center',
                  }}
                >
                  { menu.icon }
                </ListItemIcon>
                <ListItemText primary={menu.name} sx={{ opacity: open ? 1 : 0 }} />
              </ListItemButton>
              {/* </NavLink> */}
            </ListItem>
          ))}
        </List>
          </Drawer>
          </div>
{/* ===================================================== */}
          {/* ********** 메인 콘텐츠 **********   */}
          <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
            {/* <DrawerHeader /> */}
            {/* <Typography paragraph> */}
            <div className='Container target_div'>
            <Form>
              <Outlet />
            </Form>
            </div>
            {/* </Typography> */}
          </Box>
{/* ##################################################### */}

        </Box>
      {/* ))} */}
    </div>
  );
}

export default MainPage2;