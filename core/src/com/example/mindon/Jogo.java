package com.example.mindon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class Jogo extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture macaco;
	private Texture fundo;
	private Texture pedra;
	private int tamLarguraPedra;
	private int tamAlturaPedra;
	private int estadoJogo = 0;


	private Integer larguraDispositivo;
	private Integer alturaDispositivo;
	private Integer yMacaco;
	private Boolean pulando;
	private Integer posicaoPedraHorizontal;

	private ShapeRenderer shapeRenderer;
	private Circle ciruloMacaco;
	private Circle ciruloPedra;
	List<String> palavras = new ArrayList<String>();
	private String  palavra = "";
	private Integer  pontuacao = 0;
	private int indice;

	private Boolean colidiu = false;

	//texto
	BitmapFont texto;
	BitmapFont textoPalavras;
	BitmapFont textoObjetivos;

	@Override
	public void create () { //quando o jogo é iniciado. só uma vez
		batch = new SpriteBatch();
		macaco = new Texture("macaco_pulando_invertido.png");
		fundo = new Texture("back.png");
		pedra = new Texture("pedra.png");
		yMacaco = 0;
		pulando = false;

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();

		posicaoPedraHorizontal = larguraDispositivo;
		tamAlturaPedra = macaco.getHeight()/2;
		tamLarguraPedra = macaco.getWidth();


		ciruloMacaco = new Circle();
		ciruloPedra = new Circle();
		shapeRenderer = new ShapeRenderer();

		texto = new BitmapFont();
		texto.setColor(Color.BLACK);
		texto.getData().setScale(5);

		textoPalavras = new BitmapFont();
		textoPalavras.setColor(Color.BLACK);
		textoPalavras.getData().setScale(4);

		textoObjetivos = new BitmapFont();
		textoObjetivos.setColor(Color.BLACK);
		textoObjetivos.getData().setScale(4);

		palavras.add("Bed");
		palavras.add("Cheap");
		palavras.add("Peace");
		palavras.add("Advice");
		palavras.add("Funny");
		palavras.add("Lawyer");
		palavras.add("Annoying");
		palavras.add("Tired");
		palavras.add("Fork");
		palavras.add("Sick");

		indice = 0;
		palavra = palavras.get(indice);

	}

	@Override
	public void render () { //chamado varias vezes. todu momento
		verificarEstadoJogo();
		desenhar();
		colisao();
	}

	private void verificarEstadoJogo(){
		if (estadoJogo == 0){
			if (Gdx.input.justTouched()){
				if (!pulando){
					yMacaco= yMacaco+550;
				}
			}else {
				if (yMacaco>0){
					yMacaco = yMacaco-10;
					pulando = true;
				}else{
					pulando = false;
				}
			}


			posicaoPedraHorizontal-=15;
			if(posicaoPedraHorizontal < -macaco.getWidth()/2){
				posicaoPedraHorizontal = larguraDispositivo;
				colidiu = false;
				if (indice >= palavras.size() - 1){
					indice = 0;
				}else{
					indice +=1;
				}

				palavra = palavras.get(indice);

			}

		}

	}

	private void desenhar(){
		batch.begin(); //identifica que estou comecando a desenhar
		batch.draw(fundo,0,0,larguraDispositivo, alturaDispositivo);
		batch.draw(macaco,0,yMacaco);
		batch.draw(pedra,posicaoPedraHorizontal,0,tamLarguraPedra,tamAlturaPedra);
		texto.draw(batch,"Ajetivos",larguraDispositivo/2 - 200,+alturaDispositivo - alturaDispositivo/4 );

		textoPalavras.draw(batch,palavra,posicaoPedraHorizontal +tamLarguraPedra/4,tamAlturaPedra/2);

		textoObjetivos.draw(batch,"Objetivo " +pontuacao+"/5",larguraDispositivo - larguraDispositivo/4,alturaDispositivo -40);

		batch.end();
	}

		@Override
	public void dispose () { //quando o jogo é descartado. economizar recursos

	}

	private void colisao(){
		ciruloMacaco.set(0+macaco.getWidth()/2,yMacaco+ macaco.getHeight()/2,macaco.getWidth()/2);
		ciruloPedra.set(posicaoPedraHorizontal+tamLarguraPedra/2,0 + tamAlturaPedra/2,tamLarguraPedra/2);

		if( Intersector.overlaps(ciruloMacaco,ciruloPedra) ){
			if (!colidiu){
				if(palavras.get(indice) == "Cheap" || palavras.get(indice) == "Funny" ||palavras.get(indice) == "Tired" ||palavras.get(indice) == "Annoying" ||palavras.get(indice) == "Sick"  ){
					palavras.remove(indice);
					pontuacao+=1;
					colidiu = true;
				}
			}
		}

//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//
//		shapeRenderer.setColor(Color.RED);
//		shapeRenderer.circle(0+macaco.getWidth()/2,yMacaco+ macaco.getHeight()/2,macaco.getWidth()/2);
//		shapeRenderer.circle(posicaoPedraHorizontal+tamLarguraPedra/2,0 + tamAlturaPedra/2,tamLarguraPedra/2);
//
//
//		shapeRenderer.end();
	}
}
