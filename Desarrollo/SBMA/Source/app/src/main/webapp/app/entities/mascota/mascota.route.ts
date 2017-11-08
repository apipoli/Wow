import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MascotaComponent } from './mascota.component';
import { MascotaDetailComponent } from './mascota-detail.component';
import { MascotaPopupComponent } from './mascota-dialog.component';
import { MascotaDeletePopupComponent } from './mascota-delete-dialog.component';
import {MisMascotasComponent} from './mis-mascotas.component';

@Injectable()
export class MascotaResolvePagingParams implements Resolve<any> {

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

export const mascotaRoute: Routes = [
    {
        path: 'mis-mascotas',
        component: MisMascotasComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mis Mascotas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mascota',
        component: MascotaComponent,
        resolve: {
            'pagingParams': MascotaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mis Mascotas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mascota/:id',
        component: MascotaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mi Mascota'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mascotaPopupRoute: Routes = [
    {
        path: 'mascota-new',
        component: MascotaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mascotas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mascota/:id/edit',
        component: MascotaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mascotas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mascota/:id/delete',
        component: MascotaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Mascotas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
