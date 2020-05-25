import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JadeTestModule } from '../../../test.module';
import { ModeleCommandeUpdateComponent } from 'app/entities/modele-commande/modele-commande-update.component';
import { ModeleCommandeService } from 'app/entities/modele-commande/modele-commande.service';
import { ModeleCommande } from 'app/shared/model/modele-commande.model';

describe('Component Tests', () => {
  describe('ModeleCommande Management Update Component', () => {
    let comp: ModeleCommandeUpdateComponent;
    let fixture: ComponentFixture<ModeleCommandeUpdateComponent>;
    let service: ModeleCommandeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JadeTestModule],
        declarations: [ModeleCommandeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ModeleCommandeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModeleCommandeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModeleCommandeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ModeleCommande(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ModeleCommande();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
