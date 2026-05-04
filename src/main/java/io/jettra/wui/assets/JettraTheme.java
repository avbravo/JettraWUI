package io.jettra.wui.assets;

public class JettraTheme {
    /**
     * Devuelve el CSS global con soporte para múltiples temas (3d, dark, white, material, futuristic, modern).
     * @return String con el código CSS.
     */
    public static String getCSS() {
        return "<style>\n" +
            ":root {\n" +
            "  --jettra-bg: #0a0a0f;\n" +
            "  --jettra-glow: rgba(0, 240, 255, 0.4);\n" +
            "  --jettra-glass: rgba(20, 25, 40, 0.6);\n" +
            "  --jettra-border: rgba(100, 200, 255, 0.2);\n" +
            "  --jettra-text: #e0f0ff;\n" +
            "  --jettra-accent: #00e5ff;\n" +
            "  --jt-anim-duration: 0.3s;\n" +
            "}\n" +
            "body.theme-material {\n" +
            "  --jettra-bg: #f5f5f7;\n" +
            "  --jettra-glow: rgba(0, 0, 0, 0.1);\n" +
            "  --jettra-glass: rgba(255, 255, 255, 0.9);\n" +
            "  --jettra-border: rgba(0, 0, 0, 0.08);\n" +
            "  --jettra-text: #1d1d1f;\n" +
            "  --jettra-accent: #0071e3;\n" +
            "}\n" +
            "body.theme-futuristic {\n" +
            "  --jettra-bg: #050510;\n" +
            "  --jettra-glow: rgba(255, 0, 255, 0.6);\n" +
            "  --jettra-glass: rgba(15, 15, 35, 0.8);\n" +
            "  --jettra-border: rgba(255, 0, 255, 0.3);\n" +
            "  --jettra-text: #fdf2f8;\n" +
            "  --jettra-accent: #ff00ff;\n" +
            "}\n" +
            "body.theme-modern {\n" +
            "  --jettra-bg: #000000;\n" +
            "  --jettra-glow: rgba(16, 185, 129, 0.4);\n" +
            "  --jettra-glass: rgba(20, 20, 20, 0.95);\n" +
            "  --jettra-border: #333333;\n" +
            "  --jettra-text: #ffffff;\n" +
            "  --jettra-accent: #10b981;\n" +
            "}\n" +
            "body.theme-white {\n" +
            "  --jettra-bg: #f8fafc;\n" +
            "  --jettra-glow: rgba(2, 132, 199, 0.2);\n" +
            "  --jettra-glass: rgba(255, 255, 255, 0.95);\n" +
            "  --jettra-border: rgba(2, 132, 199, 0.1);\n" +
            "  --jettra-text: #0f172a;\n" +
            "  --jettra-accent: #0284c7;\n" +
            "}\n" +
            "body.theme-material-plain {\n" +
            "  --jettra-bg: #fdfdfd;\n" +
            "  --jettra-glow: rgba(0, 0, 0, 0);\n" +
            "  --jettra-glass: #ffffff;\n" +
            "  --jettra-border: #e0e0e0;\n" +
            "  --jettra-text: #202124;\n" +
            "  --jettra-accent: #1a73e8;\n" +
            "}\n" +
            "body.theme-plain {\n" +
            "  --jettra-bg: #fff;\n" +
            "  --jettra-glow: transparent;\n" +
            "  --jettra-glass: #fff;\n" +
            "  --jettra-border: #ccc;\n" +
            "  --jettra-text: #333;\n" +
            "  --jettra-accent: #000;\n" +
            "}\n" +
            "body.theme-tailwindcss {\n" +
            "  --jettra-bg: #f3f4f6;\n" +
            "  --jettra-glow: transparent;\n" +
            "  --jettra-glass: #ffffff;\n" +
            "  --jettra-border: #e5e7eb;\n" +
            "  --jettra-text: #1f2937;\n" +
            "  --jettra-accent: #3b82f6;\n" +
            "}\n" +
            "body.theme-tailwindcss-3d {\n" +
            "  --jettra-bg: #1e293b;\n" +
            "  --jettra-glow: rgba(56, 189, 248, 0.4);\n" +
            "  --jettra-glass: rgba(15, 23, 42, 0.8);\n" +
            "  --jettra-border: #334155;\n" +
            "  --jettra-text: #f8fafc;\n" +
            "  --jettra-accent: #38bdf8;\n" +
            "}\n" +
            "body.theme-dark {\n" +
            "  --jettra-bg: #020617;\n" +
            "  --jettra-glow: rgba(56, 189, 248, 0.3);\n" +
            "  --jettra-glass: rgba(15, 23, 42, 0.9);\n" +
            "  --jettra-border: rgba(56, 189, 248, 0.1);\n" +
            "  --jettra-text: #f1f5f9;\n" +
            "  --jettra-accent: #38bdf8;\n" +
            "}\n" +
            "body.theme-3d {\n" +
            "  --jettra-bg: #0a0a0f;\n" +
            "  --jettra-glow: rgba(0, 240, 255, 0.5);\n" +
            "  --jettra-glass: rgba(20, 30, 50, 0.7);\n" +
            "  --jettra-border: rgba(0, 255, 255, 0.3);\n" +
            "  --jettra-text: #e0f0ff;\n" +
            "  --jettra-accent: #00e5ff;\n" +
            "}\n" +
            "body.theme-cyberpunk {\n" +
            "  --jettra-bg: #0b0114;\n" +
            "  --jettra-glow: rgba(255, 0, 85, 0.6);\n" +
            "  --jettra-glass: rgba(20, 0, 40, 0.85);\n" +
            "  --jettra-border: #ff0055;\n" +
            "  --jettra-text: #00f2ff;\n" +
            "  --jettra-accent: #ff0055;\n" +
            "}\n" +
            "body.theme-neon {\n" +
            "  --jettra-bg: #000;\n" +
            "  --jettra-glow: rgba(57, 255, 20, 0.6);\n" +
            "  --jettra-glass: rgba(0, 20, 0, 0.9);\n" +
            "  --jettra-border: #39ff14;\n" +
            "  --jettra-text: #fff;\n" +
            "  --jettra-accent: #39ff14;\n" +
            "}\n" +
            "body.theme-glass {\n" +
            "  --jettra-bg: q-gradient(linear, left top, left bottom, from(#1e293b), to(#0f172a));\n" +
            "  --jettra-glow: rgba(255, 255, 255, 0.1);\n" +
            "  --jettra-glass: rgba(255, 255, 255, 0.05);\n" +
            "  --jettra-border: rgba(255, 255, 255, 0.1);\n" +
            "  --jettra-text: #fff;\n" +
            "  --jettra-accent: rgba(255, 255, 255, 0.8);\n" +
            "}\n" +
            "@keyframes jtGlowPulse { 0% { box-shadow: 0 0 5px var(--jettra-glow); } 50% { box-shadow: 0 0 20px var(--jettra-glow); } 100% { box-shadow: 0 0 5px var(--jettra-glow); } }\n" +
            "body {\n" +
            "  background-color: var(--jettra-bg);\n" +
            "  color: var(--jettra-text);\n" +
            "  font-family: 'Inter', system-ui, -apple-system, sans-serif;\n" +
            "  margin: 0; min-height: 100vh;\n" +
            "  overflow-x: hidden;\n" +
            "  transition: background 0.5s ease, color 0.5s ease;\n" +
            "}\n" +
            "body.theme-3d { background-image: radial-gradient(circle at 50% 50%, #1a1a2e 0%, #0a0a0f 100%); }\n" +
            "body.theme-futuristic { background-image: radial-gradient(circle, #1a0a2a 0%, #050510 100%); }\n" +
            "body.theme-cyberpunk { background-image: radial-gradient(circle, #2a0134 0%, #0b0114 100%); }\n" +
            "body.theme-neon { background-image: linear-gradient(135deg, #000 0%, #051a05 100%); }\n" +
            "body.theme-dark { background-image: linear-gradient(to bottom, #0f172a, #020617); }\n" +
            "body.theme-material-plain { background: #fdfdfd; }\n" +
            "body.theme-plain { background: #fff; }\n" +
            "body.theme-tailwindcss { background: #f3f4f6; }\n" +
            "body.theme-tailwindcss-3d { background-image: linear-gradient(to bottom right, #1e293b, #0f172a); }\n" +
            "/* Tailwind helpers embedded */\n" +
            "body.theme-tailwindcss .j-center, body.theme-tailwindcss .j-left, body.theme-tailwindcss .j-top, body.theme-tailwindcss .j-footer { box-shadow: 0 1px 3px 0 rgba(0,0,0,0.1),0 1px 2px 0 rgba(0,0,0,0.06); border-radius: 0.5rem; border: none; }\n" +
            "body.theme-material-plain .j-3d-effect, body.theme-plain .j-3d-effect, body.theme-tailwindcss .j-3d-effect, body.theme-white .j-3d-effect { box-shadow: none !important; transform: none !important; }\n" +
            ".j-dashboard {\n" +
            "  display: grid; grid-template-columns: 260px 1fr; grid-template-rows: 64px 1fr auto;\n" +
            "  grid-template-areas: 'top top' 'left center' 'footer footer';\n" +
            "  min-height: 100vh; gap: 12px; padding: 12px; box-sizing: border-box; width: 100%;\n" +
            "}\n" +
            ".j-top { grid-area: top; display: flex; justify-content: space-between; align-items: center; padding: 0 20px !important; backdrop-filter: blur(20px); border: 1px solid var(--jettra-border); border-radius: 12px; z-index: 2000; background: var(--jettra-glass); }\n" +
            ".j-left { grid-area: left; background: var(--jettra-glass); border: 1px solid var(--jettra-border); border-radius: 12px; backdrop-filter: blur(15px); overflow-y: auto; }\n" +
            ".j-center { grid-area: center; background: var(--jettra-glass); border: 1px solid var(--jettra-border); border-radius: 12px; padding: 20px; backdrop-filter: blur(10px); min-height: 500px; }\n" +
            ".j-footer { grid-area: footer; text-align: center; padding: 15px; background: var(--jettra-glass); border: 1px solid var(--jettra-border); border-radius: 12px; font-size: 0.85rem; opacity: 0.8; }\n" +
            ".j-3d-effect { transition: all var(--jt-anim-duration); }\n" +
            ".j-3d-effect:hover { transform: translateY(-3px); box-shadow: 0 10px 20px rgba(0,0,0,0.2); }\n" +
            ".j-btn { cursor: pointer; border-radius: 8px; padding: 10px 20px; border: 1px solid var(--jettra-border); background: rgba(255,255,255,0.05); color: var(--jettra-text); font-weight: 600; text-transform: uppercase; letter-spacing: 0.5px; transition: all 0.3s; display: inline-flex; align-items: center; justify-content: center; gap: 8px; }\n" +
            ".j-btn:hover { background: rgba(255,255,255,0.1); border-color: var(--jettra-accent); box-shadow: 0 0 10px var(--jettra-glow); transform: translateY(-2px); }\n" +
            ".j-btn-primary { background: linear-gradient(135deg, var(--jettra-accent), #0891b2); color: #000; border: none; box-shadow: 0 4px 15px rgba(0,255,255,0.3); }\n" +
            ".j-input { background: rgba(0,0,0,0.3); border: 1px solid var(--jettra-border); color: var(--jettra-text); padding: 12px 16px; border-radius: 8px; width: 100%; transition: all 0.3s; }\n" +
            ".j-input:focus { border-color: var(--jettra-accent); box-shadow: 0 0 10px var(--jettra-glow); outline: none; }\n" +
            ".j-table { width: 100%; border-collapse: separate; border-spacing: 0; }\n" +
            ".j-table th { background: rgba(0,255,255,0.05); color: var(--jettra-accent); text-align: left; padding: 15px; font-weight: 700; border-bottom: 2px solid var(--jettra-border); }\n" +
            ".j-table td { padding: 15px; border-bottom: 1px solid var(--jettra-border); transition: background 0.2s; }\n" +
            ".j-table tr:hover td { background: rgba(255,255,255,0.02); }\n" +
            ".j-avatar { width: 40px; height: 40px; border-radius: 50%; border: 2px solid var(--jettra-accent); overflow: hidden; display: flex; align-items: center; justify-content: center; background: #000; cursor: pointer; transition: all 0.3s; }\n" +
            ".j-avatar:hover { transform: scale(1.1); box-shadow: 0 0 15px var(--jettra-glow); }\n" +
            ".j-avatar img { width: 100%; height: 100%; object-fit: cover; }\n" +
            ".j-avatar-dropdown { position: absolute; top: 70px; right: 20px; background: var(--jettra-glass); backdrop-filter: blur(25px); border: 1px solid var(--jettra-border); border-radius: 12px; min-width: 180px; display: none; z-index: 5000; box-shadow: 0 20px 50px rgba(0,0,0,0.5); overflow: hidden; }\n" +
            ".j-avatar-dropdown a { display: block; padding: 12px 20px; color: var(--jettra-text); text-decoration: none; transition: background 0.2s; border-bottom: 1px solid rgba(255,255,255,0.05); }\n" +
            ".j-avatar-dropdown a:hover { background: rgba(0,255,255,0.1); color: var(--jettra-accent); }\n" +
            ".j-select-icon-trigger { cursor: pointer; display: flex; align-items: center; gap: 8px; padding: 8px 12px; border-radius: 20px; background: rgba(0,0,0,0.4); border: 1px solid var(--jettra-border); transition: all 0.3s; }\n" +
            ".j-select-icon-trigger:hover { border-color: var(--jettra-accent); box-shadow: 0 0 10px var(--jettra-glow); }\n" +
            ".j-select-icon-dropdown { position: absolute; top: calc(100% + 10px); right: 0; background: var(--jettra-glass); backdrop-filter: blur(25px); border: 1px solid var(--jettra-border); border-radius: 12px; padding: 8px; display: none; min-width: 160px; z-index: 5000; box-shadow: 0 15px 40px rgba(0,0,0,0.5); }\n" +
            ".j-select-icon-item { padding: 10px 15px; border-radius: 8px; cursor: pointer; transition: all 0.2s; display: flex; align-items: center; gap: 12px; }\n" +
            ".j-select-icon-item:hover { background: rgba(0,255,255,0.1); transform: translateX(5px); }\n" +
            ".j-toggle-slider { width: 44px; height: 22px; background: #333; border-radius: 11px; position: relative; cursor: pointer; border: 1px solid var(--jettra-border); transition: 0.3s; }\n" +
            ".j-toggle-slider:before { content: ''; position: absolute; width: 16px; height: 16px; left: 2px; bottom: 2px; background: #fff; border-radius: 50%; transition: 0.3s; box-shadow: 0 2px 5px rgba(0,0,0,0.3); }\n" +
            "input:checked + .j-toggle-slider { background: var(--jettra-accent); }\n" +
            "input:checked + .j-toggle-slider:before { transform: translateX(22px); }\n" +
            "/* Mobile Styles */\n" +
            "  .show-on-mobile { display: none !important; }\n" +
            "  .show-on-low-res { display: none !important; }\n" +
            "  .j-menu-toggle { cursor: pointer; display: none; align-items: center; justify-content: center; width: 36px; height: 36px; min-width: 36px; padding: 0 !important; color: var(--jettra-accent); font-size: 20px; background: rgba(0,255,255,0.1); border: 1px solid var(--jettra-border); border-radius: 50%; transition: all 0.3s; margin-right: 10px; }\n  .j-menu-toggle:hover { background: rgba(0,255,255,0.2); border-color: var(--jettra-accent); box-shadow: 0 0 10px var(--jettra-glow); }\n  .j-menu-toggle svg { width: 18px; height: 18px; stroke: currentColor; }\n" +
            "  /* Mobile & Tablet Styles */\n" +
            "  @media (max-width: 1024px) {\n" +
            "    .j-dashboard { grid-template-columns: 1fr; grid-template-areas: 'top' 'center' 'footer'; }\n" +
            "    .j-left { position: fixed; left: -280px; width: 260px; height: calc(100vh - 40px); top: 20px; z-index: 3000; transition: left 0.4s cubic-bezier(0.19, 1, 0.22, 1); background: var(--jettra-glass); backdrop-filter: blur(25px); }\n" +
            "    .j-left.active { left: 12px; box-shadow: 0 0 50px rgba(0,0,0,0.8); }\n" +
            "    .j-top { margin-left: 0; padding: 0 15px !important; }\n" +
            "    .hide-on-mobile { display: none !important; }\n" +
            "    .show-on-mobile { display: flex !important; }\n" +
            "    .j-menu-toggle { display: flex !important; }\n" +
            "    .show-on-low-res { display: inline-block !important; }\n" +
            "    }\n" +
            "    /* Sync Popup 3D Styles */\n" +
            "    .j-sync-popup-3d { position: fixed; bottom: 20px; right: 20px; background: rgba(10, 10, 20, 0.95); backdrop-filter: blur(20px); border: 1px solid var(--jettra-accent); border-radius: 15px; padding: 20px; z-index: 99999; box-shadow: 0 0 40px rgba(0, 255, 255, 0.2); animation: jSlideIn 0.6s cubic-bezier(0.23, 1, 0.32, 1); max-width: 320px; color: #fff; border-left: 5px solid var(--jettra-accent); }\n" +
            "    .j-sync-content { display: flex; align-items: flex-start; gap: 15px; }\n" +
            "    .j-sync-icon { font-size: 28px; animation: jPulse 2s infinite; filter: drop-shadow(0 0 10px var(--jettra-accent)); }\n" +
            "    .j-sync-text strong { display: block; color: var(--jettra-accent); margin-bottom: 5px; font-size: 1.1rem; text-transform: uppercase; letter-spacing: 1px; }\n" +
            "    .j-sync-text p { margin: 0; font-size: 0.85rem; opacity: 0.9; line-height: 1.4; }\n" +
            "    .j-sync-actions { display: flex; gap: 10px; margin-top: 15px; justify-content: flex-end; }\n" +
            "    .j-sync-actions button { background: rgba(0,255,255,0.05); border: 1px solid var(--jettra-accent); color: var(--jettra-accent); padding: 6px 14px; border-radius: 6px; cursor: pointer; transition: all 0.3s; font-size: 0.8rem; font-weight: bold; text-transform: uppercase; }\n" +
            "    .j-sync-actions button:hover { background: var(--jettra-accent); color: #000; box-shadow: 0 0 15px var(--jettra-accent); }\n" +
            "    @keyframes jSlideIn { from { transform: translateX(50px); opacity: 0; } to { transform: translateX(0); opacity: 1; } }\n" +
            "    @keyframes jPulse { 0% { opacity: 0.7; transform: scale(1); } 50% { opacity: 1; transform: scale(1.1); } 100% { opacity: 0.7; transform: scale(1); } }\n" +
            "</style>";
    }

    /**
     * Devuelve el JavaScript necesario para manejar cambios de tema y efectos 3D en el cliente.
     * @return String con el código JS.
     */
    public static String getJS() {
        return "<script>\n" +
            "function jtFire(id) {\n" +
            "  const el = document.getElementById(id);\n" +
            "  const form = el ? el.closest('form') : null;\n" +
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
            "    window.location.href = url.href;\n" +
            "  }\n" +
            "}\n" +
            "function changeTheme(theme) {\n" +
            "  document.body.className = 'theme-' + theme;\n" +
            "  localStorage.setItem('jettra-theme', theme);\n" +
            "  window.location.reload();\n" +
            "}\n" +
            "function toggleMobileMenu() {\n" +
            "  const menu = document.querySelector('.j-left');\n" +
            "  if(menu) menu.classList.toggle('active');\n" +
            "}\n" +
            "function toggleAvatarMenu() {\n" +
            "  const menu = document.getElementById('user-avatar-dropdown');\n" +
            "  if(menu) menu.style.display = menu.style.display === 'block' ? 'none' : 'block';\n" +
            "}\n" +
            "function toggleSelectIcon(id) {\n" +
            "  const d = document.getElementById(id + '_dropdown');\n" +
            "  if(d) d.style.display = d.style.display === 'block' ? 'none' : 'block';\n" +
            "}\n" +
            "document.addEventListener('DOMContentLoaded', () => {\n" +
            "  const savedTheme = localStorage.getItem('jettra-theme') || '3d';\n" +
            "  document.body.className = 'theme-' + savedTheme;\n" +
            "  document.addEventListener('click', (e) => {\n" +
            "    if(!e.target.closest('.j-avatar-wrapper')) {\n" +
            "      const m = document.getElementById('user-avatar-dropdown'); if(m) m.style.display = 'none';\n" +
            "    }\n" +
            "    if(!e.target.closest('.j-select-icon-trigger')) {\n" +
            "      document.querySelectorAll('.j-select-icon-dropdown').forEach(d => d.style.display='none');\n" +
            "    }\n" +
            "    // Close mobile menu when clicking outside or on a link\n" +
            "    if (window.innerWidth <= 1024) {\n" +
            "      const menu = document.querySelector('.j-left');\n" +
            "      if (menu && menu.classList.contains('active') && !e.target.closest('.j-left') && !e.target.closest('.show-on-mobile')) {\n" +
            "        menu.classList.remove('active');\n" +
            "      }\n" +
            "    }\n" +
            "  });\n" +
            "});\n" +
            "</script>";
    }
}
