package com.nyanner.govii.elementary.controller.knowledge;

import com.nyanner.govii.elementary.annotation.Log;
import com.nyanner.govii.elementary.common.BusinessType;
import com.nyanner.govii.elementary.entity.knowledge.KnowledgeTicket;
import com.nyanner.govii.elementary.entity.knowledge.Ticket;
import com.nyanner.govii.elementary.service.knowledge.TicketService;
import com.nyanner.govii.elementary.vo.RestResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.nyanner.govii.elementary.common.TicketStatusCode.*;

@RestController
@Slf4j
@RequestMapping("/api/ticket")
@AllArgsConstructor
public class TicketController {
    private TicketService ticketService;

    @GetMapping("/selectUserTickets")
    public RestResponse selectUserTickets(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return RestResponse.ok(ticketService.selectTicket(limit, page));
    }

    @RequestMapping(value = "/selectAuditingTickets", method = RequestMethod.GET)
    public RestResponse selectAuditingTicket(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return RestResponse.ok(ticketService.selectTicketByStatus(AUDITING_TICKETS, page, limit));
    }

    @GetMapping("/selectToReceiveTickets")
    public RestResponse selectToReceiveTicket(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return RestResponse.ok(ticketService.selectTicketByStatus(AUDIT_PASS_TICKETS, page, limit));
    }

    @GetMapping("/selectFinishedTickets")
    public RestResponse selectFinishedTicket(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return RestResponse.ok(ticketService.selectTicketWhenFinishAudit(page, limit));
    }


    @GetMapping("/selectCreatedTicketByUserId")
    public RestResponse selectCreatedTicketByUserId() {
        return RestResponse.ok(ticketService.selectCreatedTicketByUserId());
    }

//    @GetMapping("/selectAuditingTicket")
//    public RestResponse selectTicketByStatus(String status) {
//        return RestResponse.ok(ticketService.selectTicketByStatus(status));
//    }

    @Log(title = "??????", businessType = BusinessType.INSERT)
    @PostMapping("/addTicket")
    public RestResponse addTicket(@RequestBody Ticket ticket) {
        ticketService.addTicket(ticket);
        return RestResponse.ok();
    }

    //???????????????????????????
    @Log(title = "??????", businessType = BusinessType.UPDATE)
    @PostMapping("/audit")
    public RestResponse auditTicket(@RequestBody Ticket ticket) {
        ticketService.updateTicketWhenAudit(ticket);
        return RestResponse.ok();
    }

    //????????????????????????????????????
    @Log(title = "??????", businessType = BusinessType.UPDATE)
    @PostMapping("/receive")
    public RestResponse receiveTicket(@RequestBody Ticket ticket) {
        ticketService.updateTicketWhenReceive(ticket);
        return RestResponse.ok();
    }

    //????????????
    @Log(title = "??????", businessType = BusinessType.UPDATE)
    @PostMapping("/finish")
    public RestResponse finishTicket(@RequestBody KnowledgeTicket knowledgeTicket) {
        ticketService.updateTicketWhenFinish(knowledgeTicket);
        return RestResponse.ok();
    }

    @Log(title = "??????", businessType = BusinessType.UPDATE)
    @PostMapping("/close")
    public RestResponse closeTicket(@RequestBody Ticket ticket) {
        ticketService.closeTicket(ticket);
        return RestResponse.ok();
    }

}
