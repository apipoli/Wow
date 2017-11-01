import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Distrito } from './distrito.model';
import { DistritoPopupService } from './distrito-popup.service';
import { DistritoService } from './distrito.service';
import { Provincia, ProvinciaService } from '../provincia';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-distrito-dialog',
    templateUrl: './distrito-dialog.component.html'
})
export class DistritoDialogComponent implements OnInit {

    distrito: Distrito;
    isSaving: boolean;

    provincias: Provincia[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private distritoService: DistritoService,
        private provinciaService: ProvinciaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.provinciaService.query()
            .subscribe((res: ResponseWrapper) => { this.provincias = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.distrito.id !== undefined) {
            this.subscribeToGuardarResponse(
                this.distritoService.update(this.distrito));
        } else {
            this.subscribeToGuardarResponse(
                this.distritoService.create(this.distrito));
        }
    }

    private subscribeToGuardarResponse(result: Observable<Distrito>) {
        result.subscribe((res: Distrito) =>
            this.onGuardarSuccess(res), (res: Response) => this.onGuardarError());
    }

    private onGuardarSuccess(result: Distrito) {
        this.eventManager.broadcast({ name: 'distritoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onGuardarError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProvinciaById(index: number, item: Provincia) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-distrito-popup',
    template: ''
})
export class DistritoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private distritoPopupService: DistritoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.distritoPopupService
                    .open(DistritoDialogComponent as Component, params['id']);
            } else {
                this.distritoPopupService
                    .open(DistritoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
