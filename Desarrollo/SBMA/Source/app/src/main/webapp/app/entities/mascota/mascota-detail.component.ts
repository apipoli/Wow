import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Mascota } from './mascota.model';
import { MascotaService } from './mascota.service';

@Component({
    selector: 'jhi-mascota-detail',
    templateUrl: './mascota-detail.component.html'
})
export class MascotaDetailComponent implements OnInit, OnDestroy {

    mascota: Mascota;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private mascotaService: MascotaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMascotas();
    }

    load(id) {
        this.mascotaService.find(id).subscribe((mascota) => {
            this.mascota = mascota;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMascotas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'mascotaListModification',
            (response) => this.load(this.mascota.id)
        );
    }
}
