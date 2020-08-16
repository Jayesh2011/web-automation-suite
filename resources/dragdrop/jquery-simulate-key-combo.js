/*jshint camelcase:true, plusplus:true, forin:true, noarg:true, noempty:true, eqeqeq:true, bitwise:true, strict:true, undef:true, unused:true, curly:true, browser:true, devel:true, maxerr:100, white:false, onevar:false */
/*jslint white: true vars: true browser: true todo: true */
/*global jQuery:true $:true */

/* jQuery Simulate Key-Combo Plugin 1.2.0
 * http://github.com/j-ulrich/jquery-simulate-ext
 * 
 * Copyright (c) 2013 Jochen Ulrich
 * Licensed under the MIT license (MIT-LICENSE.txt).
 */

/**
 * 
 * For details about key events, key codes, char codes etc. see http://unixpapa.com/js/key.html
 */

;(function($,undefined) {
	"use strict";

	/**
	 * Key codes of the modifier keys.
	 * @private
	 * @author julrich
	 * @since 1.0
	 */
	var ModifierKeyCodes = {
		BACKSPACE: 		8,
		TAB: 			9,
		ENTER: 			13,
		SHIFT:			16,
		CONTROL:		17,
		ALT:			18,
		ESC:			27,
		SPACE: 			32,
		PAGE_UP:		33,
		PAGE_DOWN:		34,
		END: 			35,
		HOME: 			36,
		ARROW_LEFT: 	37,
		ARROW_UP: 		38,
		ARROW_RIGHT: 	39,
		ARROW_DOWN: 	40,
		INSERT: 		45,
		DELETE: 		46,
		COMMAND:		91,
		F1: 			112,
		F2: 			113,
		F3: 			114,
		F4: 			115,
		F5: 			116,
		F6: 			117,
		F7: 			118,
		F8: 			119,
		F9: 			120,
		F10: 			121,
		F11: 			122,
		F12: 			123
	};

	$.extend( $.simulate.prototype,
			
	/**
	 * @lends $.simulate.prototype
	 */		
	{
		
		
		/**
		 * Simulates simultaneous key presses
		 * 
		 * @see https://github.com/j-ulrich/jquery-simulate-ext/blob/master/doc/key-combo.md
		 * @public
		 * @author julrich
		 * @since 1.0
		 */
		simulateKeyCombo: function() {
			var target = $(this.target),
				options = $.extend({
					combo: "",
					eventsOnly: false
				}, this.options),
				eventOptions = {},
				combo = options.combo,
				comboSplit = combo.split(/(\+)/),
				plusExpected = false,
				holdKeys = [],
				i;
			
			if (combo.length === 0) {
				return;
			}
				
			
			// Remove empty parts
			comboSplit = $.grep(comboSplit, function(part) {
				return (part !== "");
			});
			for (i=0; i < comboSplit.length; i+=1) {
				var key = comboSplit[i],
					keyLowered = key.toLowerCase();
				
				if (plusExpected) {
					if (key !== "+") {
						throw 'Syntax error: expected "+"';
					}
				}
				else {
					var keyCode;
					switch (keyLowered) {
						case "backspace":
						case "tab":
						case "enter":
						case "space":
						case "home":
						case "end": 
						case "arrowup":
						case "arrowleft":
						case "arrowright":
						case "arrowdown":
						case "ctrl":
						case "alt":
						case "shift":
						case "esc":
						case "meta":
						case "insert":
						case "delete":
						case "f1":
						case "f2":
						case "f3":
						case "f4":
						case "f5":
						case "f6":
						case "f7":
						case "f8":
						case "f9":
						case "f10":
						case "f11":
						case "f12":
						case "pageup":
						case "pagedown":
						switch (keyLowered) {
							case "backspace": keyCode = ModifierKeyCodes.BACKSPACE; break;
							case "tab": keyCode = ModifierKeyCodes.TAB; break;
							case "enter": keyCode = ModifierKeyCodes.ENTER; break;
							case "space": keyCode = ModifierKeyCodes.SPACE; break;
							case "home": keyCode = ModifierKeyCodes.HOME; break;
							case "end": keyCode = ModifierKeyCodes.END; break;
							case "arrowup": keyCode = ModifierKeyCodes.ARROW_UP; break;
							case "arrowleft": keyCode = ModifierKeyCodes.ARROW_LEFT; break;
							case "arrowright": keyCode = ModifierKeyCodes.ARROW_RIGHT; break;
							case "arrowdown": keyCode = ModifierKeyCodes.ARROW_DOWN; break;
							case "ctrl":	keyCode = ModifierKeyCodes.CONTROL; break;
							case "alt":		keyCode = ModifierKeyCodes.ALT; break;
							case "shift":	keyCode = ModifierKeyCodes.SHIFT; break;
							case "esc":	keyCode = ModifierKeyCodes.ESC; break;
							case "meta":	keyCode = ModifierKeyCodes.COMMAND; break;
							case "insert": keyCode = ModifierKeyCodes.INSERT; break;
							case "delete": keyCode = ModifierKeyCodes.DELETE; break;
							case "f1": keyCode = ModifierKeyCodes.F1; break;
							case "f2": keyCode = ModifierKeyCodes.F2; break;
							case "f3": keyCode = ModifierKeyCodes.F3; break;
							case "f4": keyCode = ModifierKeyCodes.F4; break;
							case "f5": keyCode = ModifierKeyCodes.F5; break;
							case "f6": keyCode = ModifierKeyCodes.F6; break;
							case "f7": keyCode = ModifierKeyCodes.F7; break;
							case "f8": keyCode = ModifierKeyCodes.F8; break;
							case "f9": keyCode = ModifierKeyCodes.F9; break;
							case "f10": keyCode = ModifierKeyCodes.F10; break;
							case "f11": keyCode = ModifierKeyCodes.F11; break;
							case "f12": keyCode = ModifierKeyCodes.F12; break;
							case "pageup": keyCode = ModifierKeyCodes.PAGE_UP; break;
							case "pagedown": keyCode = ModifierKeyCodes.PAGE_DOWN; break;
						}
						eventOptions[keyLowered+"Key"] = true;
						holdKeys.unshift(keyCode);
						eventOptions.keyCode = keyCode;
						target.simulate("keydown", eventOptions);
						break;
					default:
						if (key.length > 1) {
							throw 'Syntax error: expecting "+" between each key';
						}
						else {
							keyCode = $.simulate.prototype.simulateKeySequence.prototype.charToKeyCode(key);
							holdKeys.unshift(keyCode);
							eventOptions.keyCode = keyCode;
							eventOptions.which = keyCode;
							eventOptions.charCode = undefined;
							target.simulate("keydown", eventOptions);
							if (eventOptions.shiftKey) {
								key = key.toUpperCase();
							}
							eventOptions.keyCode = key.charCodeAt(0);
							eventOptions.charCode = eventOptions.keyCode;
							eventOptions.which = eventOptions.keyCode;
							//target.simulate("keypress", eventOptions);
							if (options.eventsOnly !== true && !eventOptions.ctrlKey && !eventOptions.altKey && !eventOptions.metaKey) {
								target.simulate('key-sequence', {sequence: key, triggerKeyEvents: false});
							}
						}
						break;
					}
				}
					
				plusExpected = !plusExpected;
			}
			
			if (!plusExpected) {
				throw 'Syntax error: expected key (trailing "+"?)';
			}
			
			// Release keys
			eventOptions.charCode = undefined;
			for (i=holdKeys.length-1; i >=0 ; i-=1) {
				eventOptions.keyCode = holdKeys[i];
				eventOptions.which = holdKeys[i];
				switch (eventOptions.keyCode) {
					case ModifierKeyCodes.ALT:
						eventOptions.altKey = false;
						break;
					case ModifierKeyCodes.BACKSPACE:
						eventOptions.backspaceKey = false;
						break;
					case ModifierKeyCodes.TAB:
						eventOptions.tabKey = false;
						break;
					case ModifierKeyCodes.ENTER:
						eventOptions.enterKey = false;
						break;
					case ModifierKeyCodes.SPACE:
						eventOptions.spaceKey = false;
						break;
					case ModifierKeyCodes.END:
						eventOptions.endKey = false;
						break;
					case ModifierKeyCodes.HOME:
						eventOptions.homeKey = false;
						break;
					case ModifierKeyCodes.ARROW_UP:
						eventOptions.arrowupKey = false;
						break;
					case ModifierKeyCodes.ARROW_LEFT:
						eventOptions.arrowleftKey = false;
						break;
					case ModifierKeyCodes.ARROW_RIGHT:
						eventOptions.arrowrightKey = false;
						break;
					case ModifierKeyCodes.ARROW_DOWN:
						eventOptions.arrowdownKey = false;
						break;
					case ModifierKeyCodes.SHIFT:
						eventOptions.shiftKey = false;
						break;
					case ModifierKeyCodes.ESC:
						eventOptions.escKey = false;
						break;
					case ModifierKeyCodes.CONTROL:
						eventOptions.ctrlKey = false;
						break;
					case ModifierKeyCodes.COMMAND:
						eventOptions.metaKey = false;
						break;
					case ModifierKeyCodes.INSERT:
						eventOptions.insertKey = false;
						break;
					case ModifierKeyCodes.DELETE:
						eventOptions.deleteKey = false;
						break;
					case ModifierKeyCodes.F1:
						eventOptions.f1Key = false;
						break;
					case ModifierKeyCodes.F2:
						eventOptions.f2Key = false;
						break;
					case ModifierKeyCodes.F3:
						eventOptions.f3Key = false;
						break;
					case ModifierKeyCodes.F4:
						eventOptions.f4Key = false;
						break;
					case ModifierKeyCodes.F5:
						eventOptions.f5Key = false;
						break;
					case ModifierKeyCodes.F6:
						eventOptions.f6Key = false;
						break;
					case ModifierKeyCodes.F7:
						eventOptions.f7Key = false;
						break;
					case ModifierKeyCodes.F8:
						eventOptions.f8Key = false;
						break;
					case ModifierKeyCodes.F9:
						eventOptions.f9Key = false;
						break;
					case ModifierKeyCodes.F10:
						eventOptions.f10Key = false;
						break;
					case ModifierKeyCodes.F11:
						eventOptions.f11Key = false;
						break;
					case ModifierKeyCodes.F12:
						eventOptions.f12Key = false;
						break;
					case ModifierKeyCodes.PAGE_UP:
						eventOptions.pageupKey = false;
						break;
					case ModifierKeyCodes.PAGE_DOWN:
						eventOptions.pagedownKey = false;
						break;
					default:
						break;
				}
				target.simulate("keyup", eventOptions);				
			}
		}
		
	});
}(jQuery));