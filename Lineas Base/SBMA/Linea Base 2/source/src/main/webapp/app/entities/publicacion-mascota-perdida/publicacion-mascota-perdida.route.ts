import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PublicacionMascotaPerdidaComponent } from './publicacion-mascota-perdida.component';
import { PublicacionMascotaPerdidaDetailComponent } from './publicacion-mascota-perdida-detail.component';
import { PublicacionMascotaPerdidaPopupComponent } from './publicacion-mascota-perdida-dialog.component';
import { PublicacionMascotaPerdidaDeletePopupComponent } from './publicacion-mascota-perdida-delete-dialog.component';

@Injectable()
export class PublicacionMascotaPerdidaResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const publicacionMascotaPerdidaRoute: Routes = [
    {
        path: 'mascotas-perdidas',
        component: PublicacionMascotaPerdidaComponent,
        resolve: {
            'pagingParams': PublicacionMascotaPerdidaResolvePagingParams
        },
        data: {
            authorities: [],
            pageTitle: 'Mascotas perdidas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'publicacion-mascota-perdida',
        component: PublicacionMascotaPerdidaComponent,
        resolve: {
            'pagingParams': PublicacionMascotaPerdidaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PublicacionMascotaPerdidas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'publicacion-mascota-perdida/:id',
        component: PublicacionMascotaPerdidaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PublicacionMascotaPerdidas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const publicacionMascotaPerdidaPopupRoute: Routes = [
    {
        path: 'publicacion-mascota-perdida-new',
        component: PublicacionMascotaPerdidaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PublicacionMascotaPerdidas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'publicacion-mascota-perdida/:id/edit',
        component: PublicacionMascotaPerdidaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PublicacionMascotaPerdidas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'publicacion-mascota-perdida/:id/delete',
        component: PublicacionMascotaPerdidaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PublicacionMascotaPerdidas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
