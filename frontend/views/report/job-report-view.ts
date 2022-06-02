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
                <span class="mb-0 mt-xl text-l">(<span id="txtJobStatus"></span>)</span>
              </h2>
          </vaadin-board-row>
          <vaadin-board-row>
            <vaadin-vertical-layout class="p-m">
              <vaadin-horizontal-layout class="capacity-box">
                <span class="text-m font-semibold" id="txtJobTimeLabel">Job Time</span>
                <span class="text-m font-semibold" id="txtJobTime"></span>
              </vaadin-horizontal-layout>
              <vaadin-horizontal-layout class="capacity-box">
                <span class="text-m font-semibold">Simu. StartTime</span>
                <span class="text-m font-semibold" id="txtSimStartTime"></span>
              </vaadin-horizontal-layout>
              <vaadin-horizontal-layout class="capacity-box">
                <span class="text-m font-semibold" id="txtSimEndTimeLabel">Simu. CurrentTime</span>
                <span class="text-m font-semibold" id="txtSimEndTime"></span>
              </vaadin-horizontal-layout>
            </vaadin-vertical-layout>
            <vaadin-vertical-layout class="p-m">
              <vaadin-horizontal-layout class="capacity-box">
                <span class="text-m font-semibold">Capacity</span>
                <span><span class="text-m capacity-badge"theme="badge contrast" id="txtJobCapacity"></span></span>
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

            <vaadin-vertical-layout class="p-m">
              <vaadin-horizontal-layout class="capacity-box">
                <span class="text-m font-semibold">Export</span>
                <vaadin-button id="exportCsv" theme="small">CSV</vaadin-button>
              </vaadin-horizontal-layout>
              <div id="downloadLink"></div>
            </vaadin-vertical-layout>
            
          </vaadin-board-row>
          <vaadin-board-row>
            <vaadin-vertical-layout class="p-l" theme="spacing-l">
              <vaadin-horizontal-layout class="justify-between w-full">
                <vaadin-vertical-layout>
                  <h2 class="text-xl m-0">Average of Duration time</h2>
                  <span class="text-secondary text-xs">Minute/Time</span>
                </vaadin-vertical-layout>
                <vaadin-select id="ddlDurationChartTimes" style="width: 140px;"> </vaadin-select>
              </vaadin-horizontal-layout>
              <vaadin-chart id="viewDurationChart" type="area"></vaadin-chart>
            </vaadin-vertical-layout>
          </vaadin-board-row>
          <vaadin-board-row>
            <vaadin-vertical-layout class="p-l" theme="spacing-l">
              <vaadin-horizontal-layout class="justify-between w-full">
                <vaadin-vertical-layout>
                  <h2 class="text-xl m-0">Number of requests</h2>
                  <span class="text-secondary text-xs">Number/Time</span>
                </vaadin-vertical-layout>
                <vaadin-select id="ddlRequestChartTimes" style="width: 140px;"> </vaadin-select>
              </vaadin-horizontal-layout>
              <vaadin-chart id="viewRequestChart" type="area"></vaadin-chart>
            </vaadin-vertical-layout>
          </vaadin-board-row>
          <vaadin-board-row>
            <vaadin-vertical-layout class="p-l" theme="spacing-l">
              <vaadin-vertical-layout>
                <h2 class="text-xl m-0">Exponential Chart for duration</h2>
                <span class="text-secondary text-xs">Number/Minute</span>
              </vaadin-vertical-layout>
              <vaadin-chart id="viewExponentialChart" type="area"></vaadin-chart>
            </vaadin-vertical-layout>
          </vaadin-board-row>
        </vaadin-board>
      </main>
    `;
  }
}
