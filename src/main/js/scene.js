import store from '../store'
import { TOGGLE_UI } from '../store/gameReducer'

// https://medium.com/@michaelwesthadley/modular-game-worlds-in-phaser-3-tilemaps-1-958fc7e6bbd6


export default class Scene extends Phaser.Scene {
  create() {
    const text = this.add.text(250, 250, 'Toggle UI', {
      backgroundColor: 'white',
      color: 'blue',
      fontSize: 48,
      pixelArt: true,
    })

    text.setInteractive({ useHandCursor: true })

    text.on('pointerup', () => {
      store.dispatch({ type: TOGGLE_UI })
    })
  }
}
