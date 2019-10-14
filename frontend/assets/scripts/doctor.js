//тут прописать отправку формы при нажатии на кнопку


function treatmentSend() {
  closeTreatment();
  //впихнуть функцию отправки формы
}


function closeTreatment() {
  const treatwindow = document.getElementsByClassName('patients_tratment')[0];
  treatwindow.style.visibility = 'hidden';
}


function openTratment() {
  const treatwindow = document.getElementsByClassName('patients_tratment')[0];
  treatwindow.style.visibility = 'visible';

}