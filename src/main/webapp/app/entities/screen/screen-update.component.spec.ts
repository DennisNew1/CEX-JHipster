/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import ScreenUpdate from './screen-update.vue';
import ScreenService from './screen.service';
import AlertService from '@/shared/alert/alert.service';

import MovieService from '@/entities/movie/movie.service';

type ScreenUpdateComponentType = InstanceType<typeof ScreenUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const screenSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<ScreenUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Screen Management Update Component', () => {
    let comp: ScreenUpdateComponentType;
    let screenServiceStub: SinonStubbedInstance<ScreenService>;

    beforeEach(() => {
      route = {};
      screenServiceStub = sinon.createStubInstance<ScreenService>(ScreenService);
      screenServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          screenService: () => screenServiceStub,
          movieService: () =>
            sinon.createStubInstance<MovieService>(MovieService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(ScreenUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.screen = screenSample;
        screenServiceStub.update.resolves(screenSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(screenServiceStub.update.calledWith(screenSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        screenServiceStub.create.resolves(entity);
        const wrapper = shallowMount(ScreenUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.screen = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(screenServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        screenServiceStub.find.resolves(screenSample);
        screenServiceStub.retrieve.resolves([screenSample]);

        // WHEN
        route = {
          params: {
            screenId: '' + screenSample.id,
          },
        };
        const wrapper = shallowMount(ScreenUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.screen).toMatchObject(screenSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        screenServiceStub.find.resolves(screenSample);
        const wrapper = shallowMount(ScreenUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
