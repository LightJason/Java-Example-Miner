import store from '../store'
import { TOGGLE_UI } from '../store/gameReducer'

// https://medium.com/@michaelwesthadley/modular-game-worlds-in-phaser-3-tilemaps-1-958fc7e6bbd6

// https://gameartpartners.com/downloads/the-mines-platformer-tileset/
// https://gameartpartners.com/downloads/the-huge-super-tiny-bundle-48-hour-sale/

// https://gameartpartners.com/downloads/super-tiny-gold-miners-3-pack-of-characters/
// https://gameartpartners.com/downloads/super-tiny-cowboys/

// https://gameartpartners.com/downloads/indiana-adventurer/


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
