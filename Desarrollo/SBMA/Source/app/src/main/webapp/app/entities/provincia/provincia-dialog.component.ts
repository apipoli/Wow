import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Provincia } from './provincia.model';
import { ProvinciaPopupService } from './provincia-popup.service';
import { ProvinciaService } from './provincia.service';
import { Region, RegionService } from '../region';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-provincia-dialog',
    templateUrl: './provincia-dialog.component.html'
})
export class ProvinciaDialogComponent implements OnInit {

    provincia: Provincia;
    isSaving: boolean;

    regions: Region[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private provinciaService: ProvinciaService,
        private regionService: RegionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.regionService.query()
            .subscribe((res: ResponseWrapper) => { this.regions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.provincia.id !== undefined) {
            this.subscribeToGuardarResponse(
                this.provinciaService.update(this.provincia));
        } else {
            this.subscribeToGuardarResponse(
                this.provinciaService.create(this.provincia));
        }
    }

    private subscribeToGuardarResponse(result: Observable<Provincia>) {
        result.subscribe((res: Provincia) =>
            this.onGuardarSuccess(res), (res: Response) => this.onGuardarError());
    }

    private onGuardarSuccess(result: Provincia) {
        this.eventManager.broadcast({ name: 'provinciaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onGuardarError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRegionById(index: number, item: Region) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-provincia-popup',
    template: ''
})
export class ProvinciaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private provinciaPopupService: ProvinciaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.provinciaPopupService
                    .open(ProvinciaDialogComponent as Component, params['id']);
            } else {
                this.provinciaPopupService
                    .open(ProvinciaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
