<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="booking">
        <h2 class="jh-entity-heading" data-cy="bookingDetailsHeading">
          <span v-text="t$('jhipsterApp.booking.detail.title')"></span> {{ booking.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('jhipsterApp.booking.bookingID')"></span>
          </dt>
          <dd>
            <span>{{ booking.bookingID }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.booking.movieTime')"></span>
          </dt>
          <dd>
            <span v-if="booking.movieTime">{{ formatDateLong(booking.movieTime) }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.booking.seatID')"></span>
          </dt>
          <dd>
            <span>{{ booking.seatID }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.booking.paymentStatus')"></span>
          </dt>
          <dd>
            <span>{{ booking.paymentStatus }}</span>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.booking.movie')"></span>
          </dt>
          <dd>
            <div v-if="booking.movie">
              <router-link :to="{ name: 'MovieView', params: { movieId: booking.movie.id } }">{{ booking.movie.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('jhipsterApp.booking.customer')"></span>
          </dt>
          <dd>
            <div v-if="booking.customer">
              <router-link :to="{ name: 'CustomerView', params: { customerId: booking.customer.id } }">{{
                booking.customer.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="booking.id" :to="{ name: 'BookingEdit', params: { bookingId: booking.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./booking-details.component.ts"></script>
