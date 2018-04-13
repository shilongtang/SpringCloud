import Vue from 'vue';
import Router from 'vue-router';
import HelloWorld from '@/components/HelloWorld'
import {muenList } from '../utils/api';
import view from '../components/view';
import layout from '../components/layout';
import menuListPage from '../components/menuList';
import menuTab from '../components/menuTab';
import apps from '../apps';
import config from '../config';
import commons from '../utils/fetch';
import index from '../apps/index/desktop';

Vue.use(Router);

/*export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    }
  ]
})*/
function buildRouter(parent,router, apps) {
  router.forEach(route => {
    const tag = apps.find(app => app.name === route.name);
    route.component = tag.component;
    if(route.meta){
      route.meta.parent = parent;
      route.meta.children = route.children;
    }else{
      route.meta = {parent:parent,children: route.children};
    }
    if (route.children) {
      buildRouter(route,route.children, apps);
    }
  });
}
export default new Promise((resolve, reject) => {

  console.log("xxxxxxxxxxxx")
  muenList().then(result=>{
    const list = [];
    const menuList = result.body.content;

    menuList.forEach(menu => {
      menu = menu.functionMenu;
      const route = apps.find(route => route.name === menu.code);
      if (route) {
        menu.path = route.path;
        menu.name = route.name;
        menu.meta = route.meta;
        menu.component = route.component;
        list.push(menu);

        apps.filter(item => item.name.indexOf(route.name)===0&&item.name!==route.name)
          .forEach(item=>{
            const menuNew = {};
            for (let key in menu) {
              menuNew[key] = menu[key];
            }
            menuNew.path = item.path;
            menuNew.name = item.name;
            menuNew.meta = item.meta;
            menuNew.hidden = true;
            menuNew.component = item.component;
            list.push(menuNew);
          });
      } else {
        menu.name = menu.code;
        menu.path = menu.code;
        if (menu.level === config.menuLevel) {

          let b = false;
          const children = menuList.filter(
            item => item.functionMenu.parentId === menu.id);

          if (children.length > 0) {
            b = children.every(item => {
              return !menuList.find(
                obj => obj.functionMenu.parentId === item.functionMenu.id);
            });
          }

          if (b) {
            menu.redirect = {name: children[0].functionMenu.name || children[0].functionMenu.code};
            menu.component = menuTab;
          } else {
            menu.component = menuListPage;
          }
        } else {
          menu.component = view;
        }
        list.push(menu);
      }

    });

    const newList = [];

    list.forEach(item => {
      const newItem = {};
      for (let key in item) {
        if (key !== 'component') {
          newItem[key] = item[key];
        }
      }
      newList.push(newItem);
    });

    const routerList = commons.TreeUtils.list2tree(newList);
    buildRouter(null, routerList, list);

    routerList.push({
      name: 'index',
      path: '/index',
      hidden:true,
      component: index,
    });
    debugger
    const router = new Router({
      routes: [
        {
          name: 'main',
          path: '/',
          redirect:'index',
          component: layout,
          children: routerList,
        }
      ],
    });
  })
});
