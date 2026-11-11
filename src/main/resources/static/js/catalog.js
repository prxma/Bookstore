// Counts the selected books, shows the number on the cart and enables or disables the button.
(function () {
  var form = document.getElementById('orderForm');
  var btn = document.getElementById('cartBtn');
  var badge = document.getElementById('cartCount');
  if (!form || !btn || !badge) return;           // guests do not have these elements

  function update(animate) {
    var n = form.querySelectorAll('input[name="bookIds"]:checked').length;
    badge.textContent = n;
    badge.hidden = n === 0;
    btn.disabled = n === 0;                      // nothing selected: the button is grey and not clickable
    btn.title = n === 0 ? 'Select books with the Add to order button' : 'Buy selected: ' + n;

    if (animate && n > 0) {                      // short "pop" of the badge
      badge.classList.remove('pop');
      void badge.offsetWidth;                    // makes the browser restart the animation
      badge.classList.add('pop');
    }
  }

  form.addEventListener('change', function (e) {
    if (e.target && e.target.name === 'bookIds') update(true);
  });

  // The browser Back button restores checked boxes, so recount when the page is shown
  window.addEventListener('pageshow', function () { update(false); });
  update(false);
})();