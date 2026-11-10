(function () {
  var form = document.getElementById('orderForm');
  var btn = document.getElementById('cartBtn');
  var badge = document.getElementById('cartCount');
  if (!form || !btn || !badge) return;

  function update(animate) {
    var n = form.querySelectorAll('input[name="bookIds"]:checked').length;
    badge.textContent = n;
    badge.hidden = n === 0;
    btn.disabled = n === 0;
    btn.title = n === 0 ? 'Отметьте книги кнопкой «В заказ»' : 'Купить выбранные: ' + n;

    if (animate && n > 0) {
      badge.classList.remove('pop');
      void badge.offsetWidth;
      badge.classList.add('pop');
    }
  }

  form.addEventListener('change', function (e) {
    if (e.target && e.target.name === 'bookIds') update(true);
  });

  window.addEventListener('pageshow', function () { update(false); });
  update(false);
})();