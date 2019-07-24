package admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import admin.bean.FileVO;
import product.bean.ProductDTO;
import product.bean.ProductPaging;
import product.dao.ProductDAO;

@Controller
@RequestMapping(value="/admin/product/**")
public class AdminProductController {
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private ProductPaging productPaging;
	
	/* 재고 관리*/
	//재고 관리 화면
	@RequestMapping(value="/inventoryManage.do",method = RequestMethod.GET)
	public ModelAndView inventoryManage(@RequestParam(required=false,defaultValue="1") String pg) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("pg",pg);
		mav.addObject("adminDisplay", "/admin/product/inventoryManage.jsp");
		mav.setViewName("/main/home");
		return mav;
	}	

	//글목록 가져오기(3게시물,3블록 표시)
	@RequestMapping(value="/inventoryList.do",method= RequestMethod.POST)
	public ModelAndView inventoryList(@RequestParam(required=false,defaultValue="1") String pg){
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;		
		Map<String,String> map = new HashMap<String,String>();
		map.put("tableName", "product");		
		int totalA = productDAO.getTotalA(map);
		
		List<ProductDTO> inventoryList = productDAO.inventoryList(startNum, endNum);
	
		//페이징 처리
		productPaging.setCurrentPage(page);
		productPaging.setPageBlock(3);
		productPaging.setPageSize(3);
		productPaging.setTotalA(totalA);
		productPaging.makePagingHTML();;	
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("productPaging", productPaging);
		mav.addObject("inventoryList", inventoryList);
		mav.setViewName("jsonView");
		return mav;
	}
	//검색어 제한 글목록
	@RequestMapping(value="/inventorySearch.do",method= RequestMethod.POST)
	public ModelAndView inventorySearch(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("startNum", startNum+"");
		map.put("endNum", endNum+"");
		map.put("searchOption",searchOption);
		map.put("keyword", keyword);
		map.put("tableName", "product");
		map.put("joinName", "product_name");		
		List<ProductDTO> inventorySearchList = productDAO.inventorySearch(map);
		System.out.println(inventorySearchList);
		ModelAndView mav = new ModelAndView();
		int totalA = productDAO.getTotalSearchA(map);		
		//페이징 처리
		productPaging.setCurrentPage(page);
		productPaging.setPageBlock(3);
		productPaging.setPageSize(3);
		productPaging.setTotalA(totalA);
		productPaging.makeSearchPagingHTML();		
		mav.addObject("pg", pg);
		mav.addObject("inventorySearchList", inventorySearchList);
		mav.addObject("searchOption", searchOption);
		mav.addObject("keyword", keyword);
		mav.addObject("productPaging", productPaging);
		mav.setViewName("jsonView");
		return mav;	
	}	
	//재고 변동 모달띄우기
	@RequestMapping(value="/inventoryModify.do",method = RequestMethod.GET)
	public ModelAndView inventoryModify(@RequestParam(required=false,defaultValue="1") String pg, String productID) {
		ProductDTO productDTO = productDAO.getProductInfo(productID);
		ModelAndView mav = new ModelAndView();
		mav.addObject("pg", pg);
		mav.addObject("productDTO", productDTO);		
		mav.addObject("adminDisplay", "/admin/product/inventoryModify.jsp");
		mav.setViewName("/main/home");
		return mav;
	}	
	//재고 변동 반영
	@RequestMapping(value="/doModify.do",method= RequestMethod.POST)
	@ResponseBody
	public void doModify(@RequestParam Map<String,String> map){
		productDAO.doModify(map);
	}	
	
	/* 상품 관리*/
	//등록 관리 화면
	@RequestMapping(value="/productManage.do",method = RequestMethod.GET)
	public ModelAndView productManage(@RequestParam(required=false,defaultValue="1") String pg) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("pg",pg);
		mav.addObject("adminDisplay", "/admin/product/productManage.jsp");
		mav.setViewName("/main/home");
		return mav;
	}	
	
	//글목록 가져오기(5게시물,3블록 표시)
	@RequestMapping(value="/productList.do",method= RequestMethod.POST)
	public ModelAndView productList(@RequestParam(required=false,defaultValue="1") String pg){
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;		
		Map<String,String> map = new HashMap<String,String>();
		map.put("tableName", "product_name");
		int totalA = productDAO.getTotalA(map);
		
		List<ProductDTO> productList = productDAO.productList(startNum, endNum);
	
		//페이징 처리
		productPaging.setCurrentPage(page);
		productPaging.setPageBlock(3);
		productPaging.setPageSize(3);
		productPaging.setTotalA(totalA);
		productPaging.makePagingHTML();;	
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("productPaging", productPaging);
		mav.addObject("productList", productList);
		mav.setViewName("jsonView");
		return mav;
	}
	//검색어 제한 글목록
	@RequestMapping(value="/productSearch.do",method= RequestMethod.POST)
	public ModelAndView productSearch(@RequestParam(required=false,defaultValue="1") String pg, String keyword,String searchOption) {
		int page = Integer.parseInt(pg);
		int endNum = page*3;
		int startNum = endNum-2;
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("startNum", startNum+"");
		map.put("endNum", endNum+"");
		map.put("searchOption",searchOption);
		map.put("keyword", keyword);
		map.put("tableName", "product_name");
		map.put("joinName", "product");			
		List<ProductDTO> productSearchList = productDAO.productSearch(map);
		ModelAndView mav = new ModelAndView();
		int totalA = productDAO.getTotalSearchA(map);		
		//페이징 처리
		productPaging.setCurrentPage(page);
		productPaging.setPageBlock(3);
		productPaging.setPageSize(3);
		productPaging.setTotalA(totalA);
		productPaging.makeSearchPagingHTML();		
		mav.addObject("pg", pg);
		mav.addObject("productSearchList", productSearchList);
		mav.addObject("searchOption", searchOption);
		mav.addObject("keyword", keyword);
		mav.addObject("productPaging", productPaging);
		mav.setViewName("jsonView");
		return mav;	
	}
	
	//특정 상품 뷰로 이동
	@RequestMapping(value="/productView.do",method=RequestMethod.GET)
	public ModelAndView productView(@RequestParam(required=false,defaultValue="1") String pg, String product_name_no) {
		ModelAndView mav = new ModelAndView();
		ProductDTO productDTO = productDAO.getProduct_NameInfo(product_name_no);
		mav.addObject("productDTO",productDTO);
		mav.addObject("pg", pg);
		mav.addObject("adminDisplay", "/admin/product/productView.jsp");
		mav.setViewName("/main/home");
		return mav;
	}
	
	//상품 등록 페이지 이동
	@RequestMapping(value="/productUpload.do",method=RequestMethod.GET)
	public ModelAndView productUpload() {
		ModelAndView mav = new ModelAndView();	
		mav.addObject("adminDisplay", "/admin/product/productUpload.jsp");
		mav.setViewName("/main/home");
		return mav;
	}

	//상품 등록
	@RequestMapping(value="/doUpload.do",method=RequestMethod.POST)
	public ModelAndView doUpload(@ModelAttribute List<FileVO> data,Model map){
		
		System.out.println(data);
		for(FileVO i : data ) {
			System.out.println(i);			
		}
		/*String filePath = "D:\\Spring\\workspace\\springproject\\src\\main\\webapp\\storage";
		String fileName = product_name_image.getOriginalFilename();
		File file = new File(filePath,fileName);
		
		try {
			FileCopyUtils.copy(product_name_image.getInputStream(), new FileOutputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		productDTO.setProduct_name_image(fileName);*/

		ModelAndView mav = new ModelAndView();	
		mav.addObject("adminDisplay", "/admin/product/productUpload.jsp");
		mav.setViewName("/main/home");
		return mav;
	}
	//상품 삭제
	@RequestMapping(value="/productDelete.do", method=RequestMethod.POST)
	public String productDelete(@RequestParam String product_name_no,Model model) {
		productDAO.productDelete(product_name_no);
		model.addAttribute("pg","1");
		model.addAttribute("display", "/admin/product/productManage.jsp");
		return "/main/home";
	}
}
