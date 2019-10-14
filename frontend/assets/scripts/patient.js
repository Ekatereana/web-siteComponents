function showTreatment(){
  const treat = document.getElementsByClassName('treatment_and_receipt')[0];
  treat.style.visibility = 'visible';
}
function hideTreatment() {
  const treat = document.getElementsByClassName('treatment_and_receipt')[0];
  treat.style.visibility = 'hidden';
}

function showBill() {
  const bill = document.getElementsByClassName('bill_and_price')[0];
  bill.style.visibility = 'visible';
}
function hideBill() {
  const bill = document.getElementsByClassName('bill_and_price')[0];
  bill.style.visibility = 'hidden';
}