// Smoothly counts numbers up from zero to the value in data-count.
// Without the script the number is still visible: it is already written in the text.
(function () {
  var reduce = window.matchMedia('(prefers-reduced-motion: reduce)').matches;

  document.querySelectorAll('[data-count]').forEach(function (el) {
    var target = parseFloat(el.dataset.count);
    if (isNaN(target)) return;

    var decimals = parseInt(el.dataset.decimals || '0', 10);
    var fmt = new Intl.NumberFormat('en-US', {
      minimumFractionDigits: decimals,
      maximumFractionDigits: decimals
    });

    if (reduce) { el.textContent = fmt.format(target); return; }

    var start = null, duration = 900;
    function step(ts) {
      if (start === null) start = ts;
      var t = Math.min((ts - start) / duration, 1);
      var eased = 1 - Math.pow(1 - t, 3);          // fast start, soft stop
      el.textContent = fmt.format(target * eased);
      if (t < 1) requestAnimationFrame(step);
    }
    requestAnimationFrame(step);
  });
})();