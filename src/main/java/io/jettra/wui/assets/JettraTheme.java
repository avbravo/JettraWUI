package io.jettra.wui.assets;

public class JettraTheme {
    public static String getCSS() {
        return "<style>\n" +
            ":root {\n" +
            "  --jettra-bg: #0a0a0f;\n" +
            "  --jettra-glow: rgba(0, 240, 255, 0.4);\n" +
            "  --jettra-glass: rgba(20, 25, 40, 0.6);\n" +
            "  --jettra-border: rgba(100, 200, 255, 0.2);\n" +
            "  --jettra-text: #e0f0ff;\n" +
            "  --jettra-accent: #00e5ff;\n" +
            "}\n" +
            "body.theme-white {\n" +
            "  --jettra-bg: #f5f7fa;\n" +
            "  --jettra-glow: rgba(0, 100, 255, 0.1);\n" +
            "  --jettra-glass: rgba(255, 255, 255, 0.9);\n" +
            "  --jettra-border: rgba(0, 50, 150, 0.1);\n" +
            "  --jettra-text: #2c3e50;\n" +
            "  --jettra-accent: #3498db;\n" +
            "}\n" +
            "body.theme-dark {\n" +
            "  --jettra-bg: #121212;\n" +
            "  --jettra-glow: rgba(255, 255, 255, 0.05);\n" +
            "  --jettra-glass: rgba(30, 30, 30, 0.8);\n" +
            "  --jettra-border: rgba(255, 255, 255, 0.1);\n" +
            "  --jettra-text: #e0e0e0;\n" +
            "  --jettra-accent: #bb86fc;\n" +
            "}\n" +
            "body.theme-3d {\n" +
            "  --jettra-bg: #0a0a0f;\n" +
            "  --jettra-glow: rgba(0, 240, 255, 0.4);\n" +
            "  --jettra-glass: rgba(20, 25, 40, 0.6);\n" +
            "  --jettra-border: rgba(100, 200, 255, 0.2);\n" +
            "  --jettra-text: #e0f0ff;\n" +
            "  --jettra-accent: #00e5ff;\n" +
            "}\n" +
            "body {\n" +
            "  background-color: var(--jettra-bg);\n" +
            "  color: var(--jettra-text);\n" +
            "  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
            "  margin: 0; min-height: 100vh; display: flex; align-items: center; justify-content: center;\n" +
            "  overflow-x: hidden;\n" +
            "  transition: background-color 0.5s ease, color 0.5s ease;\n" +
            "}\n" +
            "body.theme-3d {\n" +
            "  background-image: radial-gradient(circle at 50% 50%, #151525 0%, #0a0a0f 100%);\n" +
            "}\n" +
            "body.theme-white {\n" +
            "  background-image: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);\n" +
            "}\n" +
            "body.theme-dark {\n" +
            "  background-image: linear-gradient(180deg, #121212 0%, #1a1a1a 100%);\n" +
            "}\n" +
            ".jettra-viewport {\n" +
            "  width: 100%; padding: 0; box-sizing: border-box;\n" +
            "  perspective: 1000px;\n" +
            "}\n" +
            ".j-dashboard {\n" +
            "  position: relative;\n" +
            "  display: grid;\n" +
            "  grid-template-columns: 250px 1fr;\n" +
            "  grid-template-rows: auto 1fr auto;\n" +
            "  grid-template-areas: 'top top' 'left center' 'footer footer';\n" +
            "  min-height: 100vh; gap: 10px; padding: 10px; box-sizing: border-box;\n" +
            "  width: 100%;\n" +
            "}\n" +
            ".j-hamburger {\n" +
            "  display: none;\n" +
            "  font-size: 28px;\n" +
            "  color: var(--jettra-accent);\n" +
            "  cursor: pointer;\n" +
            "  position: absolute;\n" +
            "  top: 20px;\n" +
            "  left: 20px;\n" +
            "  z-index: 1001;\n" +
            "  background: var(--jettra-glass);\n" +
            "  border: 1px solid var(--jettra-border);\n" +
            "  border-radius: 8px;\n" +
            "  width: 45px; height: 45px;\n" +
            "  text-align: center; line-height: 45px;\n" +
            "  box-shadow: 0 0 10px var(--jettra-glow);\n" +
            "}\n" +
            ".show-on-low-res {\n" +
            "  display: none !important;\n" +
            "}\n" +
            ".hide-on-low-res {\n" +
            "  display: inline-block !important;\n" +
            "}\n" +
            "@media (max-width: 768px) {\n" +
            "  .j-dashboard {\n" +
            "    grid-template-columns: 1fr;\n" +
            "    grid-template-areas: 'top' 'center' 'footer';\n" +
            "    padding: 10px;\n" +
            "    gap: 10px;\n" +
            "  }\n" +
            "  .j-top {\n" +
            "    margin-left: 55px;\n" + /* Space for hamburger */
            "    flex-direction: row;\n" +
            "    flex-wrap: wrap;\n" +
            "    justify-content: space-between;\n" +
            "    padding: 2px 8px;\n" +
            "    gap: 5px;\n" +
            "  }\n" +
            "  .j-top .j-btn {\n" +
            "    padding: 4px 8px !important;\n" +
            "    height: 30px !important;\n" +
            "    width: auto !important;\n" +
            "    min-width: 30px;\n" +
            "  }\n" +
            "  .j-top h2 {\n" +
            "    font-size: 0.9rem;\n" +
            "    margin: 0;\n" +
            "    width: auto;\n" +
            "    white-space: nowrap;\n" +
            "  }\n" +
            "  .j-top-right {\n" +
            "    width: 100%;\n" +
            "    justify-content: flex-end;\n" +
            "  }\n" +
            "  .hide-mobile {\n" +
            "    display: none !important;\n" +
            "  }\n" +
            "  .j-footer {\n" +
            "    font-size: 0.8rem;\n" +
            "    padding: 10px;\n" +
            "  }\n" +
            "  .show-on-low-res {\n" +
            "    display: inline-block !important;\n" +
            "  }\n" +
            "  .hide-on-low-res {\n" +
            "    display: none !important;\n" +
            "  }\n" +
            "  .j-hamburger {\n" +
            "    display: block;\n" +
            "    top: 15px; left: 15px;\n" +
            "    width: 40px; height: 40px; line-height: 40px;\n" +
            "  }\n" +
            "  .j-left {\n" +
            "    position: fixed;\n" +
            "    top: 65px;\n" +
            "    left: -300px;\n" +
            "    width: 250px;\n" +
            "    max-height: calc(100vh - 80px);\n" +
            "    z-index: 1000;\n" +
            "    transition: left 0.3s ease-in-out;\n" +
            "    overflow-y: auto;\n" +
            "  }\n" +
            "  .j-left.active {\n" +
            "    left: 10px;\n" +
            "  }\n" +
            "}\n" +
            ".j-top { grid-area: top; display: flex; justify-content: space-between; align-items: center; gap: 8px; padding: 2px 10px !important; overflow: visible; z-index: 2000; position: relative; transform: translateZ(20px); }\n" +
            ".j-left { grid-area: left; }\n" +
            ".j-center { grid-area: center; }\n" +
            ".j-footer { grid-area: footer; text-align: center; padding: 10px; }\n" +
            ".j-component, .j-top, .j-left, .j-center, .j-footer {\n" +
            "  background: var(--jettra-glass);\n" +
            "  border: 1px solid var(--jettra-border);\n" +
            "  border-radius: 12px;\n" +
            "  padding: 8px 12px;\n" +
            "  backdrop-filter: blur(10px);\n" +
            "  box-shadow: 0 8px 32px 0 rgba(0,0,0,0.37), inset 0 0 10px rgba(0,255,255,0.05);\n" +
            "  transition: transform 0.1s ease-out, box-shadow 0.3s ease;\n" +
            "  transform-style: preserve-3d;\n" +
            "}\n" +
            ".j-btn {\n" +
            "  background: linear-gradient(135deg, rgba(0,229,255,0.2) 0%, rgba(0,100,255,0.2) 100%);\n" +
            "  color: var(--jettra-accent);\n" +
            "  border: 1px solid var(--jettra-accent);\n" +
            "  padding: 10px 20px; border-radius: 8px; cursor: pointer;\n" +
            "  text-transform: uppercase; font-weight: bold; letter-spacing: 1px;\n" +
            "  box-shadow: 0 0 10px var(--jettra-glow);\n" +
            "  transition: all 0.3s ease;\n" +
            "}\n" +
            ".j-btn:hover {\n" +
            "  background: var(--jettra-accent);\n" +
            "  color: #000; box-shadow: 0 0 20px var(--jettra-glow), 0 0 40px var(--jettra-accent);\n" +
            "  transform: translateZ(10px);\n" +
            "}\n" +
            ".j-btn-primary { background: linear-gradient(135deg, #007bff 0%, #0056b3 100%) !important; color: #fff !important; border-color: #004085 !important; }\n" +
            ".j-btn-secondary { background: linear-gradient(135deg, #6c757d 0%, #495057 100%) !important; color: #fff !important; border-color: #343a40 !important; }\n" +
            ".j-btn-help { background: linear-gradient(135deg, #a855f7 0%, #7e22ce 100%) !important; color: #fff !important; border-color: #6b21a8 !important; }\n" +
            ".j-btn-danger { background: linear-gradient(135deg, #dc3545 0%, #a71d2a 100%) !important; color: #fff !important; border-color: #721c24 !important; }\n" +
            ".j-btn-info { background: linear-gradient(135deg, #0dcaf0 0%, #0aa2c0 100%) !important; color: #fff !important; border-color: #055160 !important; }\n" +
            ".j-btn-warning { background: linear-gradient(135deg, #ffc107 0%, #d39e00 100%) !important; color: #212529 !important; border-color: #856404 !important; }\n" +
            ".j-input {\n" +
            "  background: rgba(0,0,0,0.5);\n" +
            "  border: 1px solid var(--jettra-border);\n" +
            "  color: var(--jettra-text);\n" +
            "  padding: 12px; border-radius: 6px; width: 100%; box-sizing: border-box;\n" +
            "  outline: none; margin-top: 5px; margin-bottom: 15px;\n" +
            "  transition: all 0.2s;\n" +
            "}\n" +
            ".j-input:focus {\n" +
            "  border-color: var(--jettra-accent);\n" +
            "  box-shadow: inset 0 0 8px var(--jettra-glow);\n" +
            "}\n" +
            ".j-table-container {\n" +
            "  width: 100%; overflow-x: auto; -webkit-overflow-scrolling: touch;\n" +
            "}\n" +
            ".j-avatar {\n" +
            "  position: relative; display: inline-flex; align-items: center; justify-content: center;\n" +
            "  background: var(--jettra-glass); border: 1px solid var(--jettra-border);\n" +
            "  box-shadow: 0 0 10px var(--jettra-glow); color: var(--jettra-text);\n" +
            "  overflow: visible; font-weight: bold; transition: all 0.3s ease;\n" +
            "}\n" +
            ".j-avatar img {\n" +
            "  width: 100%; height: 100%; object-fit: cover;\n" +
            "}\n" +
            ".j-avatar-circle, .j-avatar-circle img {\n" +
            "  border-radius: 50%;\n" +
            "}\n" +
            ".j-avatar-rounded, .j-avatar-rounded img {\n" +
            "  border-radius: 8px;\n" +
            "}\n" +
            ".j-avatar-xs { width: 24px; height: 24px; font-size: 0.6rem; }\n" +
            ".j-avatar-sm { width: 32px; height: 32px; font-size: 0.8rem; }\n" +
            ".j-avatar-md { width: 48px; height: 48px; font-size: 1rem; }\n" +
            ".j-avatar-lg { width: 64px; height: 64px; font-size: 1.5rem; }\n" +
            ".j-avatar-xl { width: 96px; height: 96px; font-size: 2rem; }\n" +
            ".j-avatar-badge {\n" +
            "  position: absolute; bottom: 2px; right: 2px; width: 25%; height: 25%;\n" +
            "  min-width: 8px; min-height: 8px; border-radius: 50%; border: 2px solid var(--jettra-bg);\n" +
            "  background-color: #22c55e; z-index: 10;\n" +
            "}\n" +
            ".j-avatar-group {\n" +
            "  display: flex; align-items: center;\n" +
            "}\n" +
            ".j-avatar-group .j-avatar {\n" +
            "  margin-left: -15px; border: 2px solid var(--jettra-bg);\n" +
            "}\n" +
            ".j-avatar-group .j-avatar:first-child {\n" +
            "  margin-left: 0;\n" +
            "}\n" +
            ".j-avatar-group .j-avatar:hover {\n" +
            "  transform: scale(1.1) translateZ(20px);\n" +
            "  z-index: 100; box-shadow: 0 0 20px var(--jettra-accent);\n" +
            "}\n" +
            ".j-avatar-wrapper { position: relative; display: inline-block; cursor: pointer; }\n" +
            ".j-avatar-dropdown {\n" +
            "  position: absolute; top: calc(100% + 10px); right: 0;\n" +
            "  min-width: 150px; background: var(--jettra-glass);\n" +
            "  border: 1px solid var(--jettra-border); border-radius: 8px;\n" +
            "  backdrop-filter: blur(10px); box-shadow: 0 10px 25px rgba(0,0,0,0.5);\n" +
            "  display: none; flex-direction: column; overflow: visible; z-index: 1000;\n" +
            "}\n" +
            ".j-avatar-dropdown.active { display: flex; }\n" +
            ".j-avatar-dropdown a, .j-avatar-dropdown-item {\n" +
            "  padding: 10px 15px; color: var(--jettra-text); text-decoration: none;\n" +
            "  font-size: 0.9rem; transition: background 0.2s; display: flex; align-items: center; gap: 10px; border-bottom: 1px solid var(--jettra-border);\n" +
            "}\n" +
            ".j-avatar-dropdown a:last-child, .j-avatar-dropdown-item:last-child { border-bottom: none; }\n" +
            ".j-avatar-dropdown a:hover, .j-avatar-dropdown-item:hover { background: rgba(0, 229, 255, 0.1); color: var(--jettra-accent); }\n" +
            ".j-toggle { position: relative; display: inline-flex; align-items: center; cursor: pointer; margin-left: auto; }\n" +
            ".j-toggle-input { opacity: 0; width: 0; height: 0; position: absolute; }\n" +
            ".j-toggle-slider { position: relative; width: 44px; height: 24px; background-color: rgba(20, 30, 50, 0.6); transition: .3s; border-radius: 24px; border: 1px solid var(--jettra-border); box-shadow: inset 0 2px 4px rgba(0,0,0,0.5); display: flex; align-items: center; justify-content: space-between; padding: 0 5px; font-size: 10px; font-weight: bold; color: var(--jettra-text); overflow: hidden; }\n" +
            ".j-toggle-slider:before { position: absolute; content: ''; height: 18px; width: 18px; left: 2px; bottom: 2px; background-color: #888; transition: .3s; border-radius: 50%; box-shadow: 0 2px 5px rgba(0,0,0,0.5); z-index: 2; }\n" +
            ".j-toggle-input:checked + .j-toggle-slider { background-color: rgba(0, 229, 255, 0.1); border-color: var(--jettra-accent); }\n" +
            ".j-toggle-input:checked + .j-toggle-slider:before { transform: translateX(20px); background-color: var(--jettra-accent); box-shadow: 0 0 10px var(--jettra-accent); }\n" +
            ".j-toggle-on { opacity: 0; transform: translateX(-10px); transition: .3s; color: var(--jettra-accent); pointer-events: none; }\n" +
            ".j-toggle-off { opacity: 1; transform: translateX(0); transition: .3s; color: #888; pointer-events: none; }\n" +
            ".j-toggle-input:checked + .j-toggle-slider .j-toggle-on { opacity: 1; transform: translateX(0); }\n" +
            ".j-toggle-input:checked + .j-toggle-slider .j-toggle-off { opacity: 0; transform: translateX(10px); }\n" +
            "</style>\n";
    }
    
    public static String getJS() {
        return "<script>\n" +
            "function jtFire(id) {\n" +
            "  const form = document.querySelector('form');\n" +
            "  if (form) {\n" +
            "    let input = form.querySelector('input[name=\"_jtEvent\"]');\n" +
            "    if (!input) {\n" +
            "      input = document.createElement('input');\n" +
            "      input.type = 'hidden';\n" +
            "      input.name = '_jtEvent';\n" +
            "      form.appendChild(input);\n" +
            "    }\n" +
            "    input.value = id;\n" +
            "    form.submit();\n" +
            "  } else {\n" +
            "    const url = new URL(window.location.href);\n" +
            "    url.searchParams.set('_jtEvent', id);\n" +
            "    window.location.href = url.toString();\n" +
            "  }\n" +
            "}\n" +
            "window.jettraAnimated = localStorage.getItem('jettra-animated') === null ? null : localStorage.getItem('jettra-animated') === 'true';\n" +
            "function toggleJettraAnimation(checked) {\n" +
            "  window.jettraAnimated = checked;\n" +
            "  localStorage.setItem('jettra-animated', checked);\n" +
            "  if (!checked) {\n" +
            "    document.querySelectorAll('.j-component, .j-top, .j-left, .j-center, .j-footer').forEach(c => {\n" +
            "       c.style.transform = 'none';\n" +
            "       c.style.boxShadow = 'none';\n" +
            "    });\n" +
            "  } else {\n" +
            "    document.querySelectorAll('.j-component, .j-top, .j-left, .j-center, .j-footer').forEach(c => {\n" +
            "       c.style.transform = '';\n" +
            "       c.style.boxShadow = '';\n" +
            "    });\n" +
            "  }\n" +
            "}\n" +
            "function toggleAvatarMenu() {\n" +
            "  const menu = document.getElementById('user-avatar-dropdown');\n" +
            "  if (menu) menu.classList.toggle('active');\n" +
            "}\n" +
            "function changeTheme(theme) {\n" +
            "  document.body.className = 'theme-' + theme;\n" +
            "  localStorage.setItem('jettra-theme', theme);\n" +
            "}\n" +
            "document.addEventListener('DOMContentLoaded', () => {\n" +
            "  const savedTheme = localStorage.getItem('jettra-theme') || '3d';\n" +
            "  changeTheme(savedTheme);\n" +
            "  const dash = document.querySelector('.j-dashboard');\n" +
            "  if (dash && !document.querySelector('.j-hamburger')) {\n" +
            "    const hm = document.createElement('div');\n" +
            "    hm.className = 'j-hamburger';\n" +
            "    hm.innerHTML = '&#9776;';\n" +
            "    hm.onclick = () => {\n" +
            "       document.querySelector('.j-left')?.classList.toggle('active');\n" +
            "    };\n" +
            "    dash.prepend(hm);\n" +
            "  }\n" +
            "  const cb = document.getElementById('anim-toggle');\n" +
            "  if (window.jettraAnimated === null) {\n" +
            "    window.jettraAnimated = cb ? cb.hasAttribute('checked') : true;\n" +
            "    localStorage.setItem('jettra-animated', window.jettraAnimated);\n" +
            "  }\n" +
            "  if (cb) {\n" +
            "    cb.checked = window.jettraAnimated;\n" +
            "  }\n" +
            "  if (!window.jettraAnimated) {\n" +
            "    document.querySelectorAll('.j-component, .j-top, .j-left, .j-center, .j-footer').forEach(c => {\n" +
            "       c.style.transform = 'none';\n" +
            "       c.style.boxShadow = 'none';\n" +
            "    });\n" +
            "  }\n" +
            "  document.addEventListener('click', (e) => {\n" +
            "    const wrapper = document.querySelector('.j-avatar-wrapper');\n" +
            "    const menu = document.getElementById('user-avatar-dropdown');\n" +
            "    if (wrapper && !wrapper.contains(e.target)) {\n" +
            "      if (menu) menu.classList.remove('active');\n" +
            "    }\n" +
            "  });\n" +
            "});\n" +
            "document.addEventListener('mousemove', (e) => {\n" +
            "  if (!window.jettraAnimated) return;\n" +
            "  const isInteractive = e.target.closest('button, a, input, select, textarea, .j-btn, .j-toggle-slider, .j-select-icon-trigger, .j-select-icon-options, .j-avatar-wrapper, .j-avatar-dropdown, label');\n" +
            "  if (isInteractive) return;\n" +
            "  const cards = document.querySelectorAll('.j-component, .j-top, .j-left, .j-center, .j-footer');\n" +
            "  cards.forEach(card => {\n" +
            "    const rect = card.getBoundingClientRect();\n" +
            "    if(e.clientX < rect.left || e.clientX > rect.right || e.clientY < rect.top || e.clientY > rect.bottom) {\n" +
            "      card.style.transform = 'perspective(1000px) rotateX(0deg) rotateY(0deg) translateZ(0)';\n" +
            "      card.style.boxShadow = '0 8px 32px 0 rgba(0,0,0,0.37), inset 0 0 10px rgba(0,255,255,0.05)';\n" +
            "      return;\n" +
            "    }\n" +
            "    const x = e.clientX - rect.left; const y = e.clientY - rect.top;\n" +
            "    const centerX = rect.width / 2; const centerY = rect.height / 2;\n" +
            "    const rotateX = ((centerY - y) / centerY) * 5;\n" +
            "    const rotateY = ((x - centerX) / centerX) * 5;\n" +
            "    card.style.transform = `perspective(1000px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) translateZ(10px)`;\n" +
            "    card.style.boxShadow = `0 15px 40px rgba(0,255,255,0.2), inset 0 0 20px rgba(0,255,255,0.1)`;\n" +
            "  });\n" +
            "});\n" +
            "document.addEventListener('mouseleave', () => {\n" +
            "  const cards = document.querySelectorAll('.j-component, .j-top, .j-left, .j-center, .j-footer');\n" +
            "  cards.forEach(card => { card.style.transform = 'perspective(1000px) rotateX(0deg) rotateY(0deg) translateZ(0)'; card.style.boxShadow = ''; });\n" +
            "});\n" +
            "</script>\n";
    }
}
