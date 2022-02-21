import { html, LitElement, customElement } from 'lit-element';
import '@polymer/iron-icon';
import '@vaadin/vaadin-board';
import '@vaadin/vaadin-board/vaadin-board-row';
import '@vaadin/vaadin-charts/vaadin-chart';
import '@vaadin/vaadin-grid';
import '@vaadin/vaadin-icons';
import '@vaadin/vaadin-lumo-styles/all-imports';



@customElement('dashboard-view')
export class DashboardView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  render() {
    return html`
      <main>
        <vaadin-board>
          <vaadin-board-row>
            <vaadin-vertical-layout class="p-l">
              <h2 class="font-normal m-0 text-secondary text-xs">Current users</h2>
              <span id="currentUsers" class="font-semibold text-3xl"></span>
              <span id="currentUsersChange" theme="badge"></span>
            </vaadin-vertical-layout>
            <vaadin-vertical-layout class="p-l">
              <h2 class="font-normal m-0 text-secondary text-xs">View events</h2>
              <span id="viewEvents" class="text-3xl font-semibold"></span>
              <span id="viewEventsChange" theme="badge"></span>
            </vaadin-vertical-layout>
            <vaadin-vertical-layout class="p-l">
              <h2 class="font-normal m-0 text-secondary text-xs">Conversion rate</h2>
              <span id="conversionRate" class="text-3xl font-semibold"></span>
              <span id="conversionRateChange" theme="badge"></span>
            </vaadin-vertical-layout>
            <vaadin-vertical-layout class="p-l">
              <h2 class="font-normal m-0 text-secondary text-xs">Custom metric</h2>
              <span id="customMetric" class="text-3xl font-semibold"></span>
              <span id="customMetricChange" theme="badge"></span>
            </vaadin-vertical-layout>
          </vaadin-board-row>
          <vaadin-board-row>
            <vaadin-vertical-layout class="p-l" theme="spacing-l">
              <vaadin-horizontal-layout class="justify-between w-full">
                <vaadin-vertical-layout>
                  <h2 class="text-xl m-0">View events</h2>
                  <span class="text-secondary text-xs">Cumulative (city/month)</span>
                </vaadin-vertical-layout>
                <vaadin-select id="year" style="width: 100px;"> </vaadin-select>
              </vaadin-horizontal-layout>
              <vaadin-chart id="viewEventsChart" type="area"></vaadin-chart>
            </vaadin-vertical-layout>
          </vaadin-board-row>
          <vaadin-board-row>
            <vaadin-vertical-layout class="p-l" theme="spacing-l">
              <vaadin-horizontal-layout>
                <vaadin-vertical-layout>
                  <h2 class="text-xl m-0">Service health</h2>
                  <span class="text-secondary text-xs">Input / output</span>
                </vaadin-vertical-layout>
              </vaadin-horizontal-layout>
              <vaadin-grid id="serviceHealthGrid" theme="no-border"> </vaadin-grid>
            </vaadin-vertical-layout>
            <vaadin-vertical-layout class="p-l" theme="spacing-l">
              <vaadin-horizontal-layout>
                <vaadin-vertical-layout>
                  <h2 class="text-xl m-0">Response times</h2>
                  <span class="text-secondary text-xs">Average across all systems</span>
                </vaadin-vertical-layout>
              </vaadin-horizontal-layout>
              <vaadin-chart id="responseTimesChart" type="pie"></vaadin-chart>
            </vaadin-vertical-layout>
          </vaadin-board-row>
        </vaadin-board>
      </main>
    `;
  }
}
