<template>
  <div>
    <b-dropdown-item to="/movie">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.movie')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/screen">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.screen')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/booking">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.booking')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/customer">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.customer')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
