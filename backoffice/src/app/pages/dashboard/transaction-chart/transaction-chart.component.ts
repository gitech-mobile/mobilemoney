import {Component, Input, OnChanges, OnDestroy} from '@angular/core';
import {COSMIC_THEME as baseTheme, NbThemeService, NbColorHelper} from '@nebular/theme';

const baseThemeVariables = baseTheme.variables;

@Component({
  selector: 'app-transaction-chart',
  templateUrl: './transaction-chart.component.html',
  styleUrls: ['./transaction-chart.component.scss']
})
export class TransactionChartComponent implements OnDestroy, OnChanges {
  data: any;
  options: any;

  pieData: any;
  pieOptions: any;

  themeSubscription: any;
  @Input() label: [];
  @Input() stat: any ;
  @Input() periode: string;

  constructor(private theme: NbThemeService) {
  }

  ngOnChanges(): void {
    this.themeSubscription = this.theme.getJsTheme().subscribe(config => {

      const colors: any = config.variables;
      const chartjs: any = {
        axisLineColor: baseThemeVariables.separator,
          textColor: baseThemeVariables.fgText,
      };

      this.data = {
        labels: this.label,
        datasets: [{
          data: this.stat[0],
          label: 'Transaction Initier',
          backgroundColor: NbColorHelper.hexToRgbA(colors.infoLight, 0.3),
          borderColor: colors.primaryLight,
        }, {
          data: this.stat[1],
          label: 'Transaction Valide',
          backgroundColor: NbColorHelper.hexToRgbA(colors.successLight, 0.3),
          borderColor: colors.successLight,
        }, {
          data: this.stat[2],
          label: 'Transaction Annulee',
          backgroundColor: NbColorHelper.hexToRgbA(colors.danger, 0.3),
          borderColor: colors.danger,
        },
        ],
      };

      this.options = {
        responsive: true,
        maintainAspectRatio: false,

        scales: {
          xAxes: [
            {
              gridLines: {
                display: true,
                color: chartjs.axisLineColor,
              },
              ticks: {
                fontColor: chartjs.textColor,
              },
            },
          ],
          yAxes: [
            {
              gridLines: {
                display: true,
                color: chartjs.axisLineColor,
              },
              ticks: {
                fontColor: chartjs.textColor,
              },
            },
          ],
        },
        legend: {
          labels: {
            fontColor: chartjs.textColor,
          },
        },
      };

      this.pieData = {
      labels: ['Transaction Initier', 'Transaction Valide', 'Transaction Annuler'],
      datasets: [{
        data: [this.somme(this.stat[0]), this.somme(this.stat[1]), this.somme(this.stat[2])],
        backgroundColor: [colors.primaryLight,  colors.successLight, colors.danger],
      }],
    };

      this.pieOptions = {
      maintainAspectRatio: false,
      responsive: true,
      scales: {
        xAxes: [
          {
            display: false,
          },
        ],
        yAxes: [
          {
            display: false,
          },
        ],
      },
      legend: {
        labels: {
          fontColor: chartjs.textColor,
        },
      },
    };
  });
  }

  ngOnDestroy(): void {
    this.themeSubscription.unsubscribe();
  }

  somme(tab: [number]): number{
    let s = 0;
    tab.forEach(
      value => s = s + value
    );
    return s;
  }

}
