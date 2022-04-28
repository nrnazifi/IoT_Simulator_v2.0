import { html, LitElement, customElement } from 'lit-element';
import '@polymer/iron-icon';
import '@vaadin/vaadin-board';
import '@vaadin/vaadin-board/vaadin-board-row';
import '@vaadin/vaadin-charts/vaadin-chart';
import '@vaadin/vaadin-grid';
import '@vaadin/vaadin-icons';
import '@vaadin/vaadin-lumo-styles/all-imports';



@customElement('job-report-view')
export class JobReportView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  render() {
    return html`
      <main class="pb-l px-l">
        <vaadin-board>
          <vaadin-board-row>
            <h2 class="mb-0 mt-xl text-3xl">
              <span id="txtJobName">Job Name</span>
            </h2>
          </vaadin-board-row>
          <vaadin-board-row>
            <vaadin-vertical-layout class="p-m">
              <vaadin-horizontal-layout class="capacity-box">
                <span class="text-m font-semibold">Status</span>
                <span class="text-m font-semibold" id="txtJobStatus"></span>
              </vaadin-horizontal-layout>
              <vaadin-horizontal-layout class="capacity-box">
                <span class="text-m font-semibold">Start time</span>
                <span class="text-m font-semibold" id="txtJobStartTime"></span>
              </vaadin-horizontal-layout>
              <vaadin-horizontal-layout class="capacity-box">
                <span class="text-m font-semibold" id="txtJobEndTimeLabel">Current time</span>
                <span class="text-m font-semibold" id="txtJobEndTime"></span>
              </vaadin-horizontal-layout>
            </vaadin-vertical-layout>
            <vaadin-vertical-layout class="p-m">
              <vaadin-horizontal-layout class="capacity-box">
                <span class="text-m font-semibold">Capacity</span>
                <span><span class="text-m capacity-badge"theme="badge" id="txtJobCapacity"></span></span>
              </vaadin-horizontal-layout>
              <vaadin-horizontal-layout class="capacity-box">
                <span class="text-m font-semibold">Occupied</span>
                <span><span class="text-m capacity-badge"theme="badge success" id="txtJobOccupancy"></span></span>
              </vaadin-horizontal-layout>
              <vaadin-horizontal-layout class="capacity-box">
                <span class="text-m font-semibold">Vacant</span>
                <span><span class="text-m capacity-badge"theme="badge error" id="txtJobVacant"></span></span>
              </vaadin-horizontal-layout>
            </vaadin-vertical-layout>
          </vaadin-board-row>
        </vaadin-board>
      </main>
    `;
  }
}
