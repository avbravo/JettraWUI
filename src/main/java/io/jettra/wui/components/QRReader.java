package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class QRReader extends UIComponent {

    private String qrId;
    private String width = "350px";
    private String height = "350px";
    private String onScan = ""; // JS callback: e.g. "function(code) { console.log(code); }"
    private boolean active = true;

    public QRReader(String id) {
        super("div");
        this.qrId = id;
        this.setId(id);
    }

    public QRReader setWidth(String width) {
        this.width = width;
        return this;
    }

    public QRReader setHeight(String height) {
        this.height = height;
        return this;
    }

    public QRReader setOnScan(String onScan) {
        this.onScan = onScan;
        return this;
    }

    public QRReader setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();

        // Main Wrapper with Premium Styling
        sb.append("<div id=\"wrapper_").append(qrId).append("\" class=\"j-qr-reader-container\" style=\"")
          .append("width:").append(width).append(";")
          .append("display: flex; flex-direction: column; align-items: center; gap: 15px;")
          .append("padding: 20px; border-radius: 16px; background: rgba(30, 41, 59, 0.7);")
          .append("border: 1px solid rgba(0, 255, 255, 0.2); backdrop-filter: blur(10px);")
          .append("box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.37);\">\n");

        // Top Header
        sb.append("  <div style=\"width: 100%; display: flex; justify-content: space-between; align-items: center;\">\n")
          .append("    <span style=\"color: #0ff; font-weight: bold; font-size: 14px; text-transform: uppercase; letter-spacing: 1px;\">QR Scanner</span>\n")
          .append("    <select id=\"camera_select_").append(qrId).append("\" style=\"")
          .append("background: #0f172a; color: #fff; border: 1px solid rgba(0, 255, 255, 0.3);")
          .append("padding: 4px 8px; border-radius: 6px; font-size: 11px; outline: none; max-width: 150px;\">")
          .append("<option value=\"\">Selecting camera...</option></select>\n")
          .append("  </div>\n");

        // Video Viewport with animation overlays
        sb.append("  <div id=\"viewport_").append(qrId).append("\" style=\"")
          .append("width: 100%; height: ").append(height).append(";")
          .append("position: relative; overflow: hidden; border-radius: 12px; border: 2px solid rgba(0, 255, 255, 0.4);")
          .append("background: #000; display: flex; align-items: center; justify-content: center;\">\n");

        // Hidden elements for processing
        sb.append("    <video id=\"video_").append(qrId).append("\" autoplay playsinline style=\"width: 100%; height: 100%; object-fit: cover; display: none;\"></video>\n")
          .append("    <canvas id=\"canvas_").append(qrId).append("\" style=\"display: none;\"></canvas>\n");

        // Scanner overlay placeholder
        sb.append("    <div id=\"placeholder_").append(qrId).append("\" style=\"text-align: center; color: rgba(255, 255, 255, 0.6); z-index: 2;\">\n")
          .append("      <div style=\"font-size: 36px; margin-bottom: 10px;\">📷</div>\n")
          .append("      <span style=\"font-size: 12px;\">Scanner Stopped</span>\n")
          .append("    </div>\n");

        // Red Laser line
        sb.append("    <div id=\"laser_").append(qrId).append("\" style=\"")
          .append("position: absolute; left: 0; right: 0; height: 3px; background: #ff0055;")
          .append("box-shadow: 0 0 12px 2px #ff0055; display: none; z-index: 3;\"></div>\n");

        sb.append("  </div>\n");

        // Action Buttons & Toast status
        sb.append("  <div style=\"width: 100%; display: flex; gap: 10px;\">\n")
          .append("    <button id=\"btn_toggle_").append(qrId).append("\" type=\"button\" class=\"j-btn j-btn-primary\" style=\"flex: 1; font-weight: 600;\">Start Scanning</button>\n")
          .append("  </div>\n")
          .append("  <div id=\"status_").append(qrId).append("\" style=\"color: #a9b7c6; font-size: 11px; text-align: center; min-height: 15px;\">Ready</div>\n");

        sb.append("</div>\n");

        // JavaScript Logic
        sb.append("<script>\n")
          .append("(function() {\n")
          .append("  var qrId = '").append(qrId).append("';\n")
          .append("  var video = document.getElementById('video_' + qrId);\n")
          .append("  var canvas = document.getElementById('canvas_' + qrId);\n")
          .append("  var ctx = canvas.getContext('2d', { willReadFrequently: true });\n")
          .append("  var placeholder = document.getElementById('placeholder_' + qrId);\n")
          .append("  var laser = document.getElementById('laser_' + qrId);\n")
          .append("  var toggleBtn = document.getElementById('btn_toggle_' + qrId);\n")
          .append("  var cameraSelect = document.getElementById('camera_select_' + qrId);\n")
          .append("  var statusDiv = document.getElementById('status_' + qrId);\n")
          .append("  var stream = null;\n")
          .append("  var active = false;\n")
          .append("  var scanInterval = null;\n")
          .append("  var laserInterval = null;\n")
          .append("  var beepAudio = null;\n")
          .append("  var callbackScan = ").append(onScan.isEmpty() ? "null" : onScan).append(";\n\n")

          // Play audio beep function
          .append("  function playBeep() {\n")
          .append("    try {\n")
          .append("      if (!beepAudio) {\n")
          .append("        var audioCtx = new (window.AudioContext || window.webkitAudioContext)();\n")
          .append("        var osc = audioCtx.createOscillator();\n")
          .append("        var gain = audioCtx.createGain();\n")
          .append("        osc.connect(gain);\n")
          .append("        gain.connect(audioCtx.destination);\n")
          .append("        osc.type = 'sine';\n")
          .append("        osc.frequency.setValueAtTime(800, audioCtx.currentTime);\n")
          .append("        gain.gain.setValueAtTime(0, audioCtx.currentTime);\n")
          .append("        gain.gain.linearRampToValueAtTime(0.3, audioCtx.currentTime + 0.05);\n")
          .append("        gain.gain.linearRampToValueAtTime(0, audioCtx.currentTime + 0.15);\n")
          .append("        osc.start();\n")
          .append("        osc.stop(audioCtx.currentTime + 0.2);\n")
          .append("      }\n")
          .append("    } catch (e) { console.error('Audio error:', e); }\n")
          .append("  }\n\n")

          // Laser line animation
          .append("  function animateLaser() {\n")
          .append("    var top = 0;\n")
          .append("    var dir = 1.5;\n")
          .append("    var heightVal = video.parentElement.clientHeight;\n")
          .append("    laserInterval = setInterval(function() {\n")
          .append("      top += dir;\n")
          .append("      if (top >= heightVal - 5) dir = -1.5;\n")
          .append("      if (top <= 5) dir = 1.5;\n")
          .append("      laser.style.top = top + 'px';\n")
          .append("    }, 16);\n")
          .append("  }\n\n")

          // Stop scanning
          .append("  function stopScanner() {\n")
          .append("    active = false;\n")
          .append("    if (stream) {\n")
          .append("      stream.getTracks().forEach(function(track) { track.stop(); });\n")
          .append("      stream = null;\n")
          .append("    }\n")
          .append("    video.style.display = 'none';\n")
          .append("    placeholder.style.display = 'block';\n")
          .append("    laser.style.display = 'none';\n")
          .append("    toggleBtn.innerText = 'Start Scanning';\n")
          .append("    toggleBtn.style.background = '';\n")
          .append("    clearInterval(scanInterval);\n")
          .append("    clearInterval(laserInterval);\n")
          .append("    statusDiv.innerText = 'Scanner Stopped';\n")
          .append("  }\n\n")

          // Start scanning
          .append("  function startScanner() {\n")
          .append("    if (typeof jsQR === 'undefined') {\n")
          .append("      statusDiv.innerText = 'Waiting for library...';\n")
          .append("      return;\n")
          .append("    }\n")
          .append("    var constraints = {\n")
          .append("      video: cameraSelect.value ? { deviceId: { exact: cameraSelect.value } } : { facingMode: 'environment' }\n")
          .append("    };\n")
          .append("    navigator.mediaDevices.getUserMedia(constraints).then(function(mediaStream) {\n")
          .append("      stream = mediaStream;\n")
          .append("      video.srcObject = mediaStream;\n")
          .append("      video.style.display = 'block';\n")
          .append("      placeholder.style.display = 'none';\n")
          .append("      laser.style.display = 'block';\n")
          .append("      active = true;\n")
          .append("      toggleBtn.innerText = 'Stop Scanning';\n")
          .append("      toggleBtn.style.background = '#da3633';\n")
          .append("      statusDiv.innerText = 'Scanning for QR Code...';\n")
          .append("      animateLaser();\n")
          .append("      \n")
          .append("      scanInterval = setInterval(function() {\n")
          .append("        if (video.readyState === video.HAVE_ENOUGH_DATA) {\n")
          .append("          canvas.width = video.videoWidth;\n")
          .append("          canvas.height = video.videoHeight;\n")
          .append("          ctx.drawImage(video, 0, 0, canvas.width, canvas.height);\n")
          .append("          var imgData = ctx.getImageData(0, 0, canvas.width, canvas.height);\n")
          .append("          var code = jsQR(imgData.data, imgData.width, imgData.height, { inversionAttempts: 'dontInvert' });\n")
          .append("          if (code) {\n")
          .append("            playBeep();\n")
          .append("            statusDiv.innerHTML = '<span style=\"color:#4ade80; font-weight:bold;\">Scanned: ' + code.data + '</span>';\n")
          .append("            if (callbackScan) {\n")
          .append("              try { callbackScan(code.data); } catch(err) { console.error('onScan error:', err); }\n")
          .append("            }\n")
          .append("            // Custom postback event trigger if standard Jettra listener exists\n")
          .append("            var customEv = new CustomEvent('qr_scanned_' + qrId, { detail: code.data });\n")
          .append("            document.dispatchEvent(customEv);\n")
          .append("            stopScanner();\n")
          .append("          }\n")
          .append("        }\n")
          .append("      }, 250);\n")
          .append("    }).catch(function(err) {\n")
          .append("      statusDiv.innerText = 'Camera Access Error: ' + err.message;\n")
          .append("      console.error(err);\n")
          .append("    });\n")
          .append("  }\n\n")

          // Populate camera select
          .append("  function loadCameras() {\n")
          .append("    navigator.mediaDevices.enumerateDevices().then(function(devices) {\n")
          .append("      var videoDevices = devices.filter(d => d.kind === 'videoinput');\n")
          .append("      cameraSelect.innerHTML = '';\n")
          .append("      if (videoDevices.length === 0) {\n")
          .append("        var opt = document.createElement('option');\n")
          .append("        opt.value = '';\n")
          .append("        opt.innerText = 'No camera found';\n")
          .append("        cameraSelect.appendChild(opt);\n")
          .append("        return;\n")
          .append("      }\n")
          .append("      videoDevices.forEach(function(dev, idx) {\n")
          .append("        var opt = document.createElement('option');\n")
          .append("        opt.value = dev.deviceId;\n")
          .append("        opt.innerText = dev.label || 'Camera ' + (idx + 1);\n")
          .append("        cameraSelect.appendChild(opt);\n")
          .append("      });\n")
          .append("    }).catch(function(err) {\n")
          .append("      console.error('Error loading cameras:', err);\n")
          .append("    });\n")
          .append("  }\n\n")

          // Load jsQR library asynchronously
          .append("  function loadLib() {\n")
          .append("    if (typeof jsQR === 'undefined') {\n")
          .append("      if (!document.getElementById('jsqr-lib')) {\n")
          .append("        var script = document.createElement('script');\n")
          .append("        script.id = 'jsqr-lib';\n")
          .append("        script.src = 'https://cdn.jsdelivr.net/npm/jsqr@1.4.0/dist/jsQR.min.js';\n")
          .append("        document.head.appendChild(script);\n")
          .append("      }\n")
          .append("      var checkInt = setInterval(function() {\n")
          .append("        if (typeof jsQR !== 'undefined') {\n")
          .append("          clearInterval(checkInt);\n")
          .append("          statusDiv.innerText = 'Library Loaded';\n")
          .append("        }\n")
          .append("      }, 100);\n")
          .append("    } else {\n")
          .append("      statusDiv.innerText = 'Scanner Ready';\n")
          .append("    }\n")
          .append("  }\n\n")

          // Initial actions
          .append("  toggleBtn.onclick = function() {\n")
          .append("    if (active) {\n")
          .append("      stopScanner();\n")
          .append("    } else {\n")
          .append("      startScanner();\n")
          .append("    }\n")
          .append("  };\n\n")
          .append("  cameraSelect.onchange = function() {\n")
          .append("    if (active) {\n")
          .append("      stopScanner();\n")
          .append("      startScanner();\n")
          .append("    }\n")
          .append("  };\n\n")

          // Request initial permission to label cameras properly
          .append("  navigator.mediaDevices.getUserMedia({ video: true }).then(function(s) {\n")
          .append("    s.getTracks().forEach(t => t.stop());\n")
          .append("    loadCameras();\n")
          .append("  }).catch(function() {\n")
          .append("    loadCameras();\n")
          .append("  });\n\n")

          .append("  loadLib();\n")
          .append("})();\n")
          .append("</script>\n");

        setContent(sb.toString());
        return super.render();
    }

    @Override
    public QRReader setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public QRReader setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public QRReader setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public QRReader addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public QRReader removeClass(String className) {
        super.removeClass(className);
        return this;
    }
}
