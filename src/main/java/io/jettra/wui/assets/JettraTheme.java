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
            "body {\n" +
            "  background-color: var(--jettra-bg);\n" +
            "  background-image: radial-gradient(circle at 50% 50%, #151525 0%, #0a0a0f 100%);\n" +
            "  color: var(--jettra-text);\n" +
            "  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
            "  margin: 0; min-height: 100vh; display: flex; align-items: center; justify-content: center;\n" +
            "  overflow-x: hidden;\n" +
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
            "  min-height: 100vh; gap: 20px; padding: 20px; box-sizing: border-box;\n" +
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
            "  }\n" +
            "  .j-top { margin-left: 60px; /* Space for hamburger */ }\n" +
            "  .j-hamburger {\n" +
            "    display: block;\n" +
            "  }\n" +
            "  .j-left {\n" +
            "    position: fixed;\n" +
            "    top: 75px;\n" +
            "    left: -300px;\n" +
            "    width: 250px;\n" +
            "    max-height: calc(100vh - 90px);\n" +
            "    z-index: 1000;\n" +
            "    transition: left 0.3s ease-in-out;\n" +
            "    overflow-y: auto;\n" +
            "  }\n" +
            "  .j-left.active {\n" +
            "    left: 20px;\n" +
            "  }\n" +
            "}\n" +
            ".j-top { grid-area: top; display: flex; justify-content: space-between; align-items: center; }\n" +
            ".j-left { grid-area: left; }\n" +
            ".j-center { grid-area: center; }\n" +
            ".j-footer { grid-area: footer; text-align: center; }\n" +
            ".j-component, .j-top, .j-left, .j-center, .j-footer {\n" +
            "  background: var(--jettra-glass);\n" +
            "  border: 1px solid var(--jettra-border);\n" +
            "  border-radius: 12px;\n" +
            "  padding: 20px;\n" +
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
            "  width: 100%;\n" +
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
            "</style>\n";
    }
    
    public static String getJS() {
        return "<script>\n" +
            "document.addEventListener('DOMContentLoaded', () => {\n" +
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
            "});\n" +
            "document.addEventListener('mousemove', (e) => {\n" +
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
