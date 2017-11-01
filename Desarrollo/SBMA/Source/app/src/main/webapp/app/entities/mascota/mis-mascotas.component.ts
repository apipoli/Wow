import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Mascota } from './mascota.model';
import { MascotaService } from './mascota.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-mis-mascotas',
    templateUrl: './mis-mascotas.component.html'
})
export class MisMascotasComponent implements OnInit, OnDestroy {

currentAccount: any;
    mascotas: Mascota[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    reverse: any;

    constructor(
        private mascotaService: MascotaService,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private dataUtils: JhiDataUtils,
        private router: Router,
        private eventManager: JhiEventManager
    ) {}

    loadAll() {
        this.mascotaService.misMascotas().subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/mascota', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMascotas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Mascota) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInMascotas() {
        this.eventSubscriber = this.eventManager.subscribe('mascotaListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data) {
        this.mascotas = data;
    }
    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
