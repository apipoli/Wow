/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PublicacionMascotaPerdidaDetailComponent } from '../../../../../../main/webapp/app/entities/publicacion-mascota-perdida/publicacion-mascota-perdida-detail.component';
import { PublicacionMascotaPerdidaService } from '../../../../../../main/webapp/app/entities/publicacion-mascota-perdida/publicacion-mascota-perdida.service';
import { PublicacionMascotaPerdida } from '../../../../../../main/webapp/app/entities/publicacion-mascota-perdida/publicacion-mascota-perdida.model';

describe('Component Tests', () => {

    describe('PublicacionMascotaPerdida Management Detail Component', () => {
        let comp: PublicacionMascotaPerdidaDetailComponent;
        let fixture: ComponentFixture<PublicacionMascotaPerdidaDetailComponent>;
        let service: PublicacionMascotaPerdidaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [PublicacionMascotaPerdidaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PublicacionMascotaPerdidaService,
                    JhiEventManager
                ]
            }).overrideTemplate(PublicacionMascotaPerdidaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PublicacionMascotaPerdidaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PublicacionMascotaPerdidaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PublicacionMascotaPerdida(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.publicacionMascotaPerdida).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
