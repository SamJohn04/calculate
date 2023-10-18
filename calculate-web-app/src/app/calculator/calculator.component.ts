import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.css']
})
export class CalculatorComponent {
  @Input() username: string = '';
  result?: number = undefined;
  loading: boolean = false;
  async calculate(expression: string) {
    this.loading = true;
    const username = this.username;
    const res = await fetch('http://localhost:8080/calculate', {
      method: 'POST',
      body: expression,
      mode: 'cors',
      headers: {
        'Content-Type': 'plain/text',
      }
    })

    const { result, traceLog } = await res.json();
    console.log("calculator trace: ", traceLog);
    this.result = result;
    this.loading = false;

    const storeResult = await fetch('http://localhost:8090/log', {
      method: 'POST',
      body: JSON.stringify({
        username,
        expression,
        result: result.toString(),
      }),
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
      }
    })

    console.log("Store result trace: ", (await storeResult.json()).traceLog);
  }
}
