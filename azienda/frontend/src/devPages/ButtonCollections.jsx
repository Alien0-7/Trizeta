import style from '../styles/ButtonCollections.module.css';

export default function ButtonsCollection() {
  return (
    <div className={style.buttonsContainer}>
      <div className={style.buttonBox}>
        <button className={style.gradientButton}>Gradient</button>
      </div>
      <div className={`${style.buttonBox} ${style.bgDark}`}>
        <button className={style.glassButton}>Glassmorphism</button>
      </div>
      <div className={`${style.buttonBox} ${style.bgGray}`}>
        <button className={style.neumorphismButton}>Neumorphism</button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.threeDButton}>3D</button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.iconButton}>
          <i className="fas fa-play"></i> Play
        </button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.borderButton}>Animated Border</button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.floatingButton}>Floating</button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.glowingButton}>Glowing</button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.rippleButton}>Ripple Effect</button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.outlineHoverButton}>Outline Hover</button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.skewedButton}>Skewed Button</button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.shadowButton}>Shadow</button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.roundIconButton}>
          <i className="fas fa-heart"></i>
        </button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.textIconButton}>
          <i className="fas fa-download"></i> Download
        </button>
      </div>
      <div className={style.buttonBox}>
        <button className={style.loadingButton}>
          Loading
          <div className={style.spinner}></div>
        </button>
      </div>
    </div>

  );
};