import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PublicacionMascotaPerdida } from './publicacion-mascota-perdida.model';
import { PublicacionMascotaPerdidaService } from './publicacion-mascota-perdida.service';

@Component({
    selector: 'jhi-publicacion-mascota-perdida-detail',
    templateUrl: './publicacion-mascota-perdida-detail.component.html'
})
export class PublicacionMascotaPerdidaDetailComponent implements OnInit, OnDestroy {

    publicacionMascotaPerdida: PublicacionMascotaPerdida;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private publicacionMascotaPerdidaService: PublicacionMascotaPerdidaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPublicacionMascotaPerdidas();
    }

    load(id) {
        this.publicacionMascotaPerdidaService.find(id).subscribe((publicacionMascotaPerdida) => {
            this.publicacionMascotaPerdida = publicacionMascotaPerdida;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPublicacionMascotaPerdidas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'publicacionMascotaPerdidaListModification',
            (response) => this.load(this.publicacionMascotaPerdida.id)
        );
    }
}
