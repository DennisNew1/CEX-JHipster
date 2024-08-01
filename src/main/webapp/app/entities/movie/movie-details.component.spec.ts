/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import MovieDetails from './movie-details.vue';
import MovieService from './movie.service';
import AlertService from '@/shared/alert/alert.service';

type MovieDetailsComponentType = InstanceType<typeof MovieDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const movieSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Movie Management Detail Component', () => {
    let movieServiceStub: SinonStubbedInstance<MovieService>;
    let mountOptions: MountingOptions<MovieDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      movieServiceStub = sinon.createStubInstance<MovieService>(MovieService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          movieService: () => movieServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        movieServiceStub.find.resolves(movieSample);
        route = {
          params: {
            movieId: '' + 123,
          },
        };
        const wrapper = shallowMount(MovieDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.movie).toMatchObject(movieSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        movieServiceStub.find.resolves(movieSample);
        const wrapper = shallowMount(MovieDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
