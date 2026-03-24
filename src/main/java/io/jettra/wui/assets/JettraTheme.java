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
            "    font-size: 0.85rem !important;\n" +
            "    margin: 0 !important;\n" +
            "    width: auto !important;\n" +
            "  }\n" +
            "  .j-top h2 {\n" +
            "    font-size: 0.9rem;\n" +
            "    width: 100%;\n" +
            "    margin-bottom: 5px;\n" +
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
            "@media (max-width: 480px) {\n" +
            "  .j-top-right {\n" +
            "    gap: 5px !important;\n" +
            "  }\n" +
            "  .j-btn {\n" +
            "    padding: 8px 12px;\n" +
            "    font-size: 0.8rem;\n" +
            "  }\n" +
            "  .modal-content {\n" +
            "    width: 95% !important;\n" +
            "    padding: 15px !important;\n" +
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
            "</style>\n";
    }
    
    public static String getJS() {
        return "<script>\n" +
            "let jettraAnimated = localStorage.getItem('jettra-animated') === null ? true : localStorage.getItem('jettra-animated') === 'true';\n" +
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
            "  if (cb) {\n" +
            "    cb.checked = jettraAnimated;\n" +
            "    cb.addEventListener('change', (e) => {\n" +
            "       jettraAnimated = e.target.checked;\n" +
            "       localStorage.setItem('jettra-animated', jettraAnimated);\n" +
            "       if (!jettraAnimated) {\n" +
            "          document.querySelectorAll('.j-component, .j-top, .j-left, .j-center, .j-footer').forEach(c => {\n" +
            "             c.style.transform = 'perspective(1000px) rotateX(0deg) rotateY(0deg) translateZ(0)';\n" +
            "             c.style.boxShadow = '';\n" +
            "          });\n" +
            "       }\n" +
            "    });\n" +
            "  }\n" +
            "});\n" +
            "document.addEventListener('mousemove', (e) => {\n" +
            "  if (!jettraAnimated) return;\n" +
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
