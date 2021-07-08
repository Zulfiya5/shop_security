!function (a) {
    var b = "fu_popover", c = 7, d = {
        arrowShow: !0,
        autoHide: !1,
        autoHideDelay: 2500,
        content: "",
        delay: {show: 0, hide: 0},
        dismissable: !1,
        placement: "bottom",
        themeName: "default",
        title: "",
        trigger: "click",
        width: "150px"
    }, e = function (e, f) {
        return this.element = a(e), this.popoverId = b + "_" + c++, this.options = a.extend({}, d, f), this.options.autoHideDelay = void 0 === this.options.autoHideDelay ? 0 : this.options.autoHideDelay, this.options.delay.show = void 0 === this.options.delay.show ? 0 : this.options.delay.show, this.options.delay.hide = void 0 === this.options.delay.hide ? 0 : this.options.delay.hide, this.setStyles(), this.init(), this.initTriggers(), this
    };
    e.prototype.setStyles = function () {
        if ("default" === this.options.themeName) if (a("#fu_popover_styles_default").length) ; else {
            var b = ".fu_popover_" + this.options.themeName + " {position: absolute;background: #fff;border: 1px solid rgba(0, 0, 0, 0.2);border-radius: 6px;z-index: 1060;-webkit-background-clip: padding-box;background-clip: padding-box;border: 1px solid #cccccc;border: 1px solid rgba(0, 0, 0, 0.2);border-radius: 6px;-webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);}";
            b += ".fu_popover_header_" + this.options.themeName + "{margin: 0;padding: 8px 14px;font-size: 14px;text-align: center;background-color: #f7f7f7;border-bottom: 1px solid #ebebeb;border-radius: 5px 5px 0 0;}.fu_popover_content_" + this.options.themeName + "{padding: 9px 14px;}", b += ".fu_popover_" + this.options.themeName + ":after, .fu_popover_" + this.options.themeName + ':before {border: solid transparent;content: " ";height: 0;width: 0;position: absolute;pointer-events: none;}.fu_popover_' + this.options.themeName + ":after {border-color: rgba(255, 255, 255, 0);border-width: 10px;}.fu_popover_" + this.options.themeName + ":before {border-color: rgba(0, 0, 0, 0);border-width: 11px;}", b += ".arrow_top_" + this.options.themeName + ":after{left: 50%;bottom: 100%;border-bottom-color: #fff;margin-left: -10px;}.arrow_top_" + this.options.themeName + ":before{left: 50%;bottom: 100%;border-bottom-color: rgba(0, 0, 0, 0.2);margin-left: -11px;}", b += ".arrow_bottom_" + this.options.themeName + ":after{left: 50%;top: 100%;border-top-color: #fff;margin-left: -10px;}.arrow_bottom_" + this.options.themeName + ":before{left: 50%;top: 100%;border-top-color: rgba(0, 0, 0, 0.2);margin-left: -11px;}", b += ".arrow_left_" + this.options.themeName + ":after{right: 100%;top: 50%;border-right-color: #fff;margin-top: -10px;}.arrow_left_" + this.options.themeName + ":before{right: 100%;top: 50%;border-right-color: rgba(0, 0, 0, 0.2);margin-top: -11px;}", b += ".arrow_right_" + this.options.themeName + ":after{left: 100%;top: 50%;border-left-color: #fff;margin-top: -10px;}.arrow_right_" + this.options.themeName + ":before{left: 100%;top: 50%;border-left-color: rgba(0, 0, 0, 0.2);margin-top: -11px;}", a("<style type='text/css' id='fu_popover_styles_default'>" + b + "</style>").appendTo("head"), b = ".fu_progress{overflow: hidden;height: 20px;margin-bottom: 10px;background-color: #f5f5f5;border-radius: 4px;-webkit-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);}", b += ".fu_progress_bar{float: left;width: 0%;height: 100%;font-size: 12px;line-height: 20px;color: #ffffff;text-align: center;background-color: #337ab7;-webkit-box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.15);box-shadow: inset 0 -1px 0 rgba(0, 0, 0, 0.15);-webkit-transition: width 0.6s ease;-o-transition: width 0.6s ease;transition: width 0.6s ease;}", a("<style type='text/css'>" + b + "</style>").appendTo("head")
        }
    }, e.prototype.init = function () {
        var a = "";
        this.options.title.length > 0 && (a = '<div class="fu_popover_header_' + this.options.themeName + '">' + this.options.title + "</div>");
        var c = '<div class="fu_popover_' + this.options.themeName + " " + this.getArrowClass() + '" id="' + this.popoverId + '" style="display:none;">' + a + '<div class="fu_popover_content_' + this.options.themeName + '">' + this.options.content + "</div></div>";
        this.options.dismissable === !0 ? this.initDismissableEvent() : "", this.htmlStr = c
    }, e.prototype.getArrowClass = function () {
        return "top" === this.options.placement ? "arrow_bottom_" + this.options.themeName : "bottom" === this.options.placement ? "arrow_top_" + this.options.themeName : "left" === this.options.placement ? "arrow_right_" + this.options.themeName : "right" === this.options.placement ? "arrow_left_" + this.options.themeName : ""
    }, e.prototype.initDismissableEvent = function () {
        var b = this.popoverId, c = this.options.delay.hide, d = this.element;
        a(document).mouseup(function (e) {
            var f = a(d), g = a("#" + b);
            f.is(e.target) || 0 !== f.has(e.target).length || g.is(e.target) || 0 !== g.has(e.target).length || a("#" + b).hide(c)
        })
    }, e.prototype.initTriggers = function () {
        var a = this.options.trigger.split(" ");
        a = jQuery.unique(a);
        for (var b = 0; b < a.length; b++) "click" === a[b] ? this.initClickTrigger() : "hover" === a[b] ? this.initHoverTrigger() : "focus" === a[b] ? this.initFocusTrigger() : ""
    }, e.prototype.initClickTrigger = function () {
        var b = this.element;
        a("body").on("click", "#" + this.element[0].id, function () {
            a(b).fu_popover("show")
        })
    }, e.prototype.initHoverTrigger = function () {
        var b = this.element;
        a("body").on("mouseenter", "#" + this.element[0].id, function () {
            a(b).fu_popover("show")
        })
    }, e.prototype.initFocusTrigger = function () {
        var b = this.element;
        a("body").on("focus", "#" + this.element[0].id, function () {
            a(b).fu_popover("show")
        })
    }, e.prototype.display = function (b) {
        var c = this.popoverId;
        if ("show" === b) {
            var d = this.options.delay.show, e = this.options.arrowShow;
            if (a("#" + c).length || a("body").append(this.htmlStr), setTimeout(function () {
                e || (a("#" + c).attr("class", ""), a("#" + c).addClass("fu_popover_" + this.options.themeName)), a("#" + c).show()
            }, d), this.setPopupPosition(), this.options.autoHide === !0) {
                var f = this.options.autoHideDelay;
                f = 0 == f ? 2500 : f, setTimeout(function () {
                    a("#" + c).hide()
                }, f)
            }
        } else "hide" === b ? a("#" + c).hide(this.options.delay.hide) : "destroy" === b && (a("#" + c).remove(), a(this.element).removeData("fu_popover"), this.destroyTriggers())
    }, e.prototype.getOffsetSum = function (a) {
        for (var b = 0, c = 0; a;) b += parseInt(a.offsetTop), c += parseInt(a.offsetLeft), a = a.offsetParent;
        return {top: b, left: c}
    }, e.prototype.getOffsetRect = function (a) {
        var b = a.getBoundingClientRect(), c = document.body, d = document.documentElement,
            e = window.pageYOffset || d.scrollTop || c.scrollTop,
            f = window.pageXOffset || d.scrollLeft || c.scrollLeft, g = d.clientTop || c.clientTop || 0,
            h = d.clientLeft || c.clientLeft || 0, i = b.top + e - g, j = b.left + f - h;
        return {top: Math.round(i), left: Math.round(j)}
    }, e.prototype.getOffset = function (a) {
        return a.getBoundingClientRect ? this.getOffsetRect(a) : this.getOffsetSum(a)
    }, e.prototype.setPopupPosition = function () {
        a("#" + this.popoverId).css({width: this.options.width});
        var j, k, b = this.getOffset(this.element[0]),
            d = (a("#" + this.popoverId).position(), a(this.element).outerWidth()), e = a(this.element).outerHeight(),
            f = b.left, g = b.top, h = a("#" + this.popoverId).outerWidth(), i = a("#" + this.popoverId).outerHeight();
        if ("bottom" === this.options.placement || "top" === this.options.placement) {
            var l = d / 2, m = h / 2;
            k = "bottom" === this.options.placement ? g + e + 14 : g - i - 10;
            var n = f + l;
            j = n - m
        } else if ("left" === this.options.placement || "right" === this.options.placement) {
            var l = e / 2, m = i / 2;
            j = "left" === this.options.placement ? f - h - 10 : f + d + 14;
            var o = g + l;
            k = o - m
        }
        a("#" + this.popoverId).css({left: j + "px", top: k + "px"})
    }, e.prototype.destroyTriggers = function () {
        var b = this.options.trigger.split(" ");
        b = jQuery.unique(b);
        for (var c = 0; c < b.length; c++) a("body").off("click", "#" + this.element[0].id, function () {
        }), a("body").off("mouseenter", "#" + this.element[0].id, function () {
        }), a("body").off("focus", "#" + this.element[0].id, function () {
        })
    }, a.fn.fu_popover = function (c) {
        if ("string" == typeof c) {
            var d = a(this).data(b);
            if (jQuery.isEmptyObject(d)) return this;
            d.display(c)
        } else if (!a.data(this, b)) {
            var f = new e(this, c);
            return a("#" + f.element[0].id).data(b, f), a.data(this, b, f), this
        }
    }
}(jQuery);