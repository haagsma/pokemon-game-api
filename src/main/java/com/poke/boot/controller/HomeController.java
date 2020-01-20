package com.poke.boot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poke.boot.dto.*;
import com.poke.boot.model.Item;
import com.poke.boot.model.Move;
import com.poke.boot.model.Pokemon;
import com.poke.boot.model.Version;
import com.poke.boot.repository.PokemonRepository;
import com.poke.boot.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.xml.ws.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class HomeController {

    private RestTemplate restTemplate = new RestTemplate();
    private HttpEntity<String> entity;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private CustomHttpClientService httpClient;

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private MoveService moveService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PokemonMapService pokemonMapService;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/version")
    public ResponseEntity<?> getVersion() {
        Version version = em.createQuery("SELECT v FROM Version v ORDER BY v.id DESC", Version.class)
                .setMaxResults(1)
                .getSingleResult();
        return new ResponseEntity<>(version, HttpStatus.OK);
    }

    @GetMapping("/home")
    public String home() {
        return "Home Component";
    }

    @GetMapping("/getimglist")
    public ResponseEntity<?> getImgAll() throws IOException, URISyntaxException {

        /*String url;
        File outputfile;
        BufferedImage image;*/

//        List<Pokemon> pokemons = getList();
        String url;
        File outputfile;
        BufferedImage image;
        for (int i = 152;i < 809;i++) {
            url = "https://www.serebii.net/pokemongo/pokemon/" + i + ".png";
            image = ImageIO.read(new URL(url));
            outputfile = new File("/home/Haagsma/Documentos/pokemons/" + i + ".png");
            ImageIO.write(image, "png", outputfile);
        }
/*
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        entity = new HttpEntity<String>("parameters", headers);

       List<Pokemon> pokemons = getList();

       for (Pokemon pokemon: pokemons) {
           PokemonApiCoDTO pokemonApiCoDTO = restTemplate.exchange(pokemon.getUrl(), HttpMethod.GET,entity, PokemonApiCoDTO.class).getBody();
            if (pokemonApiCoDTO.getId() > 802) {
                System.out.println("id: " + pokemonApiCoDTO.getId() + "\nurl: " + pokemonApiCoDTO.getSprites().getFrontDefault());
                url = pokemonApiCoDTO.getSprites().getFrontDefault();
                 if (url != null) {
                     image = ImageIO.read(new URL(url));
                     outputfile = new File("/home/Haagsma/Documentos/pokemons/" + pokemonApiCoDTO.getId() + ".png");
                     ImageIO.write(image, "png", outputfile);
                 }
            }
       }

 */

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<Pokemon> getList() throws IOException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper();
        String res = restTemplate.getForEntity("https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json", String.class).getBody();
        PokedexGoDTO pokedexGoDTO = objectMapper.readValue(res, PokedexGoDTO.class);
        return pokedexGoDTO.getPokemon();


/*        PokedexGoDTO pokedexGoDTO = restTemplate.exchange("https://pokeapi.co/api/v2/pokemon/?offset=800&limit=999", HttpMethod.GET,entity, PokedexGoDTO.class).getBody();
        return pokedexGoDTO.getResults();*/
    }


    @GetMapping("/registerpokemoninfo")
    @Transactional
    public ResponseEntity<?> registerPokemonBasicInfo() {
        PokemonDTO pokemonDTO;
        for (int i = 1; i < 808; i++) {
            try {
                pokemonDTO = httpClient.get("https://pokeapi.co/api/v2/pokemon/" + i + "/", PokemonDTO.class);
                pokemonService.saveFromApi(pokemonDTO);
            } catch (Exception e) {
                System.out.println("Pokemon não salvo id: " + i);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/registermovesinfo")
    @Transactional
    public ResponseEntity<?> registerMovesInfo() {
        ResponseMovesApiDTO response = httpClient.get("https://pokeapi.co/api/v2/move/?limit=999", ResponseMovesApiDTO.class);

        for (GenericTypeDTO movetc: response.getResults()) {
            System.out.println("Iniciando Move: " + movetc.getUrl());
            try {
                Move move = httpClient.get(movetc.getUrl(), Move.class);
                moveService.saveMoveFromApi(move);
            } catch (HttpClientErrorException hce) {
                System.out.println("Move não encontrada no db: " + movetc.getUrl());
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/indexskilltopokemons")
    public ResponseEntity<?> indexSkillToPokemon() {
        List<Pokemon> pokemons = new ArrayList<Pokemon>((Collection<? extends Pokemon>) pokemonRepository.findAll());

        for (Pokemon pokemon: pokemons) {
            try {
                PokemonDTO pokemonDTO = httpClient.get("https://pokeapi.co/api/v2/pokemon/" + pokemon.getName() + "/", PokemonDTO.class);
                pokemonService.saveMoveOfPokemon(pokemon, pokemonDTO);
            } catch (Exception e) {
                System.out.println("Pokemon " + pokemon.getName() + " Não foi encontrado.");
            }

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/registerevolution")
    public ResponseEntity<?> registerLevel() {
        ResponseGenericApiDTO response = httpClient.get("https://pokeapi.co/api/v2/evolution-chain/?limit=999", ResponseGenericApiDTO.class);

        for (GenericTypeDTO result: response.getResults()) {
            try {
                EvolutionResponseDTO evolutionResponseDTO = httpClient.get(result.getUrl(), EvolutionResponseDTO.class);
                pokemonService.saveLevel(evolutionResponseDTO.getChain());
            } catch (Exception e) {
                System.out.println("Error: " + result.getUrl());
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/registeriteminfo")
    public ResponseEntity<?> registerItemInfo() {
        ResponseGenericApiDTO response = httpClient.get("https://pokeapi.co/api/v2/item/?limit=999", ResponseGenericApiDTO.class);

        for (GenericTypeDTO gen: response.getResults()) {
            System.out.println("Iniciando pesquisa item: " + gen.getUrl());
            try {
                ItemDTO itemDTO = httpClient.get(gen.getUrl(), ItemDTO.class);
                itemService.saveItemFromApi(itemDTO);
            } catch (Exception e) {
                System.out.println("Item não encontrado: " + gen.getUrl());
            }


        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/registerimgitem")
    public ResponseEntity<?> registerImgItem() {
        List<Item> items = itemService.getItems();
        BufferedImage image;
        File file;
        for (Item item: items) {
            try {
                image = ImageIO.read(new URL("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/" + item.getNemotecnico() +".png"));
                file = new File("/tmp/items/" + item.getNemotecnico() + ".png");
                ImageIO.write(image, "png", file);
            } catch (Exception e) {
                System.out.println("Imagem do item não encontrada: " + item.getNemotecnico());
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
